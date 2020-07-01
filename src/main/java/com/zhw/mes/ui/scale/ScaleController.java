package com.zhw.mes.ui.scale;


import com.zhw.mes.support.commport.MesSerialPort;
import com.zhw.mes.support.commport.MesSerialPortManager;
import com.zhw.mes.support.controller.RouteCallbackController;
import com.zhw.mes.support.scales.ElectricalScale;
import com.zhw.mes.support.scales.StaticElectricalScale;
import com.zhw.mes.support.scales.protocol.StaticScaleProtocolParser;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Controller
public class ScaleController extends RouteCallbackController implements Initializable {
    private ScaleModel mainModel = new ScaleModel();
    @FXML
    public ComboBox serialPortComboBox;
    @FXML
    public ComboBox stopBitComboBox;
    @FXML
    public ComboBox parityBitComboBox;
    @FXML
    public ComboBox dataBitComboBox;
    @FXML
    public ComboBox baudRateComboBox;
    @FXML
    public TextArea outputTextArea;

    ElectricalScale scale;

    Executor executor = Executors.newFixedThreadPool(3);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkCommPorts();
        serialPortComboBox.itemsProperty().bind(mainModel.serialPortsProperty());
        baudRateComboBox.itemsProperty().bind(mainModel.baudRatesProperty());
        dataBitComboBox.itemsProperty().bind(mainModel.dataBitsProperty());
        stopBitComboBox.itemsProperty().bind(mainModel.stopBitsProperty());
        parityBitComboBox.itemsProperty().bind(mainModel.parityBitsProperty());
        outputTextArea.textProperty().bind(mainModel.outputProperty());


        mainModel.serialPortNameProperty().bind(serialPortComboBox.getSelectionModel().selectedItemProperty().asString());
        mainModel.baudRateProperty().bind(baudRateComboBox.getSelectionModel().selectedItemProperty().asString());
        mainModel.dataBitProperty().bind(dataBitComboBox.getSelectionModel().selectedItemProperty().asString());
        mainModel.stopBitProperty().bind(stopBitComboBox.getSelectionModel().selectedItemProperty().asString());
        mainModel.parityBitProperty().bind(parityBitComboBox.getSelectionModel().selectedItemProperty().asString());
    }

    private void checkCommPorts() {
        CompletableFuture.supplyAsync(()->{
            List<String> serialPorts = new ArrayList<>();
            List<MesSerialPort> serialPortList = MesSerialPortManager.findSerialPorts();
            serialPortList.forEach(s-> serialPorts.add(s.getPortName()));
            return serialPorts;
        }).whenComplete((serialPorts,err)->{
            if(err != null){
                Platform.runLater(()->{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"检测串口时,发生错误.");
                    alert.showAndWait();
                });
            }else {
                Platform.runLater(()->{
                    mainModel.serialPortsProperty().setAll(serialPorts);
                    serialPortComboBox.getSelectionModel().select(1);
                    baudRateComboBox.getSelectionModel().select(0);
                    dataBitComboBox.getSelectionModel().select(0);
                    stopBitComboBox.getSelectionModel().select(1);
                    parityBitComboBox.getSelectionModel().select(0);
                });
            }
        });

    }



    public void handleStartListenAction(ActionEvent actionEvent) {
        CompletableFuture.runAsync(()->{
            try {
                scale = new StaticElectricalScale(mainModel.getSerialPortName(),
                        Integer.parseInt(mainModel.getBaudRate()),
                        Integer.parseInt(mainModel.getDataBit()),
                        Integer.parseInt(mainModel.getStopBit()),
                        Integer.parseInt(mainModel.getParityBit()),
                        (weight)->{
                            Platform.runLater(()->{
                                String text = mainModel.getOutput();
                                mainModel.setOutput(new Date().getTime()+"\t"+weight.toString()+"\r\n"+text);
                            });
                        },
                        new StaticScaleProtocolParser(),
                        "万能电子称");


                scale.start();

            } catch (Exception e) {
                e.printStackTrace();
               throw new RuntimeException(e);
            }
        }).whenComplete((none,err)->{
            if(err != null){
                Platform.runLater(()->{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"注册监听器时,发生错误.");
                    alert.showAndWait();
                });
            }
        });
    }

    public void handleStopListenAction(ActionEvent actionEvent) {
        scale.stop();
    }
}
