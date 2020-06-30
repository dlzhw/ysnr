package com.zhw.mes.support.scales.protocol;


import com.zhw.mes.support.utils.NumberUtil;

/**
 * 动态电子称协议解析器
 */
public class DynamicScaleProtocolParser implements ElectricalScaleProtocolParser {
    @Override
    public MaterialWeight parse(byte[] data) {
        int weight = NumberUtil.byte4ToInt(data,0);
        MaterialWeight mw = new MaterialWeight();
        mw.setWeight(weight);
        return mw;
    }
}
