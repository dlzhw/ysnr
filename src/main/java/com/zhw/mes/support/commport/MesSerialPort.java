package com.zhw.mes.support.commport;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;
import java.util.function.Consumer;

/**
 * 通讯端口类
 */
public class MesSerialPort {
    private String portName;
    private boolean opened;
    SerialPort serialPort;
    CommPortIdentifier identifier;

    /**
     * 打开串口
     * @param timeout 超时时间,单位毫秒
     * @throws MesSerialPortException
     */
    public void open(int timeout) throws MesSerialPortException {
        try {
            identifier = CommPortIdentifier.getPortIdentifier(portName);
            serialPort = (SerialPort) identifier.open("",timeout);
            setOpened(true);
        } catch (NoSuchPortException e) {
            throw new MesSerialPortException("串口不存在");
        } catch (PortInUseException e) {
            throw new MesSerialPortException("串口被占用,无法打开");
        }
    }

    /**
     * 设置串口参数
     * @param baudRate  波特率
     * @param dataBits  数据位
     * @param stopBit   停止位
     * @param parityBit 校验位
     * @throws MesSerialPortException
     */
    public void setSerialPortParams(int baudRate,int dataBits,int stopBit,int parityBit) throws MesSerialPortException {
        try {
            if(!opened){
                throw new MesSerialPortException("串口未打开");
            }
            serialPort.setSerialPortParams(baudRate,dataBits,stopBit,parityBit);
        } catch (UnsupportedCommOperationException e) {
            throw new MesSerialPortException("串口参数设置有误");
        }
    }

    public void close(){
        setOpened(false);
        serialPort.close();
    }

    /**
     * 注册串口监听器
     * @param consumer 数据的消费方
     */
    public void setListener(Consumer<byte[]> consumer) throws MesSerialPortException {
        try {
            serialPort.addEventListener(new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    switch (serialPortEvent.getEventType()){
                        case SerialPortEvent.DATA_AVAILABLE: {
                            try {
                                InputStream inputStream = serialPort.getInputStream();
                                byte[] buff = new byte[serialPort.getDataBits()];
                                inputStream.read(buff,0,serialPort.getDataBits());
                                consumer.accept(buff);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            serialPort.notifyOnDataAvailable(true);
            serialPort.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
            throw new MesSerialPortException("此串口注册的监听器过多,未成功注册");
        }
    }



    CommPortIdentifier getIdentifier() {
        return identifier;
    }

    void setIdentifier(CommPortIdentifier identifier) {
        setOpened(identifier.isCurrentlyOwned());
        this.identifier = identifier;
    }


    public String getPortName() {
        return portName;
    }

    void setPortName(String portName) {
        this.portName = portName;
    }

    public boolean isOpened() {
        return opened;
    }

    private void setOpened(boolean opened) {
        this.opened = opened;
    }
}
