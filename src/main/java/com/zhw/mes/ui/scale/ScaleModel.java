package com.zhw.mes.ui.scale;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ScaleModel {
    private ObservableListValue<String> serialPorts = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ObservableListValue<Integer> baudRates = new SimpleListProperty<>(FXCollections.observableArrayList(9600,4800));
    private ObservableListValue<Integer> dataBits = new SimpleListProperty<>(FXCollections.observableArrayList(8));
    private ObservableListValue<Integer> stopBits = new SimpleListProperty<>(FXCollections.observableArrayList(0,1));
    private ObservableListValue<Integer> parityBits = new SimpleListProperty(FXCollections.observableArrayList(1,2));
    private StringProperty output = new SimpleStringProperty("");

    private StringProperty serialPortName = new SimpleStringProperty("");
    private StringProperty baudRate = new SimpleStringProperty("");
    private StringProperty dataBit = new SimpleStringProperty("");
    private StringProperty parityBit = new SimpleStringProperty("");
    private StringProperty stopBit = new SimpleStringProperty("");

    public String getSerialPortName() {
        return serialPortName.get();
    }

    public StringProperty serialPortNameProperty() {
        return serialPortName;
    }

    public void setSerialPortName(String serialPortName) {
        this.serialPortName.set(serialPortName);
    }

    public ObservableList<String> getSerialPorts() {
        return serialPorts.get();
    }

    public ObservableListValue<String> serialPortsProperty() {
        return serialPorts;
    }

    public ObservableList<Integer> getBaudRates() {
        return baudRates.get();
    }

    public ObservableListValue<Integer> baudRatesProperty() {
        return baudRates;
    }

    public ObservableList<Integer> getDataBits() {
        return dataBits.get();
    }

    public ObservableListValue<Integer> dataBitsProperty() {
        return dataBits;
    }

    public ObservableList<Integer> getStopBits() {
        return stopBits.get();
    }

    public ObservableListValue<Integer> stopBitsProperty() {
        return stopBits;
    }

    public ObservableList<Integer> getParityBits() {
        return parityBits.get();
    }

    public ObservableListValue<Integer> parityBitsProperty() {
        return parityBits;
    }

    public String getOutput() {
        return output.get();
    }

    public StringProperty outputProperty() {
        return output;
    }

    public void setOutput(String output) {
        this.output.set(output);
    }

    public String getBaudRate() {
        return baudRate.get();
    }

    public StringProperty baudRateProperty() {
        return baudRate;
    }

    public void setBaudRate(String baudRate) {
        this.baudRate.set(baudRate);
    }

    public String getDataBit() {
        return dataBit.get();
    }

    public StringProperty dataBitProperty() {
        return dataBit;
    }

    public void setDataBit(String dataBit) {
        this.dataBit.set(dataBit);
    }

    public String getParityBit() {
        return parityBit.get();
    }

    public StringProperty parityBitProperty() {
        return parityBit;
    }

    public void setParityBit(String parityBit) {
        this.parityBit.set(parityBit);
    }

    public String getStopBit() {
        return stopBit.get();
    }

    public StringProperty stopBitProperty() {
        return stopBit;
    }

    public void setStopBit(String stopBit) {
        this.stopBit.set(stopBit);
    }
}
