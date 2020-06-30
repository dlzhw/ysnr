package com.zhw.mes.support.scales;


import com.zhw.mes.support.scales.protocol.MaterialWeight;

/**
 * 取物料重量接口
 */
public interface WeightConsumer {

    /**
     * 在电子称数据到达时回调本方法
     * @param weight
     */
    void accept(MaterialWeight weight);
}
