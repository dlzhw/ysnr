package com.zhw.mes.domain;

import java.util.Date;

public class Material {
    private Long id;
    private String bussinessType;
    private String mno;
    private String name;
    private double total;
    private double weight;
    private double deductWeight;
    private String warehouse;
    private Date purchaseTime;

    public Material() {

    }
/*
    public Material(Long id, String bussinessType, String no, String name, double count, double weight, double deductWeight, String warehouse, Date purchaseTime) {
        this.id = id;
        this.bussinessType = bussinessType;
        this.no = no;
        this.name = name;
        this.count = count;
        this.weight = weight;
        this.deductWeight = deductWeight;
        this.warehouse = warehouse;
        this.purchaseTime = purchaseTime;
    }*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDeductWeight() {
        return deductWeight;
    }

    public void setDeductWeight(double deductWeight) {
        this.deductWeight = deductWeight;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", bussinessType='" + bussinessType + '\'' +
                ", mno='" + mno + '\'' +
                ", name='" + name + '\'' +
                ", total=" + total +
                ", weight=" + weight +
                ", deductWeight=" + deductWeight +
                ", warehouse='" + warehouse + '\'' +
                ", purchaseTime=" + purchaseTime +
                '}';
    }
}
