package com.zhw.mes.support.scales;


import com.zhw.mes.support.scales.protocol.ElectricalScaleProtocolParser;
import com.zhw.mes.support.scales.protocol.MaterialWeight;

/**
 * 静态电子称
 */
public class StaticElectricalScale extends ElectricalScale {
    private ElectricalScaleProtocolParser protocolParser;

    /**
     * @param serialPortName  连接串口名称
     * @param baudRate
     * @param dataBits
     * @param stopBit
     * @param parityBit
     * @param weightConsumer 物料重量消费者
     * @param name            电子称名称
     */
    public StaticElectricalScale(String serialPortName, int baudRate, int dataBits, int stopBit, int parityBit, WeightConsumer weightConsumer, ElectricalScaleProtocolParser protocolParser , String name) {
        super(serialPortName, baudRate, dataBits, stopBit, parityBit, weightConsumer,name);
        this.protocolParser = protocolParser;
    }

    @Override
    protected MaterialWeight parseWeight(byte[] dataPackage) {
        return protocolParser.parse(dataPackage);
    }
}
