package com.zhw.mes.support.scales.protocol;


import com.zhw.mes.support.utils.NumberUtil;

/**
 * 静态称协议解析器
 */
public class StaticScaleProtocolParser implements ElectricalScaleProtocolParser {
    @Override
    public MaterialWeight parse(byte[] data) {
        //静态称串口数据协议规则
        //每个数据包18字节
        // 字节序
        //   1          始终为 02
        //   2,3,4      为状态信息
        //   3
        //   4
        //   5
        int weight = NumberUtil.byte4ToInt(data,0);
        MaterialWeight mw = new MaterialWeight();
        mw.setWeight(weight);
        return mw;
    }
}
