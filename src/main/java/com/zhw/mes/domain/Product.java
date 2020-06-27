package com.zhw.mes.domain;

public class Product {
    private Long id;
    private String name;
    private String mno;
    private int controllDown;
    private int shiftDown;
    private String keycode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public int getControllDown() {
        return controllDown;
    }

    public void setControllDown(int controllDown) {
        this.controllDown = controllDown;
    }

    public int getShiftDown() {
        return shiftDown;
    }

    public void setShiftDown(int shiftDown) {
        this.shiftDown = shiftDown;
    }

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode;
    }
}
