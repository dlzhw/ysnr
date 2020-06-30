package com.zhw.mes.support.commport;

/**
 * 串口异常基类
 */
public class MesSerialPortException extends RuntimeException {
    public MesSerialPortException(String message){
        super(message);
    }
}
