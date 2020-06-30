package com.zhw.mes.support.scales.protocol;

/**
 * 电子称协议解析器
 */
public interface ElectricalScaleProtocolParser {
    /**
     * 根据串口数据,解析出重量
     * @param data
     * @return
     */
    MaterialWeight parse(byte[] data);
}
