package com.zhw.mes.support.commport;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 通讯端口管理器
 */
public class MesSerialPortManager {

    /**
     * 查询所有可用串口
     * @return
     */
    public static List<MesSerialPort> findSerialPorts(){
        List<MesSerialPort> result = new ArrayList<>();
        Enumeration<CommPortIdentifier> portIdentifiers = CommPortIdentifier.getPortIdentifiers();
        while (portIdentifiers.hasMoreElements()){
            CommPortIdentifier identifier = portIdentifiers.nextElement();
            if(identifier.getPortType()==1){
                MesSerialPort mcp = new MesSerialPort();
                mcp.setPortName(identifier.getName());
                mcp.setIdentifier(identifier);
                result.add(mcp);
            }
        }
        return result;
    }


    /**
     * 根据串口名称查找指定串口
     * @param portName 串口名称
     * @return
     * @throws MesSerialPortException
     */
    public static MesSerialPort findSerialPort(String portName) throws MesSerialPortException {
        MesSerialPort serialPort = null;
        try {
            CommPortIdentifier identifier = CommPortIdentifier.getPortIdentifier(portName);
            serialPort = new MesSerialPort();
            serialPort.setPortName(identifier.getName());
            serialPort.setIdentifier(identifier);
        } catch (NoSuchPortException e) {
            throw new MesSerialPortException("串口不存在");
        }
        return serialPort;

    }
}
