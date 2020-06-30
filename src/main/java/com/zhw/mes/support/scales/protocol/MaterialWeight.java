package com.zhw.mes.support.scales.protocol;

/**
 * 物料重量
 */
public class MaterialWeight {
    //单位
    //数值
    //毛重
    //净重

    private double weight;

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "MaterialWeight{" +
                "weight=" + weight +
                '}';
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
