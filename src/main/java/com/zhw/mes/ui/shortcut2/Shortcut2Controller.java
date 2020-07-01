package com.zhw.mes.ui.shortcut2;

import com.zhw.mes.domain.Material;
import com.zhw.mes.domain.Product;
import com.zhw.mes.logic.MaterailService;
import com.zhw.mes.logic.ProductService;
import com.zhw.mes.support.controller.RouteCallbackController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Controller
public class Shortcut2Controller extends RouteCallbackController implements Initializable {
    private final Logger logger = LoggerFactory.getLogger(Shortcut2Controller.class);

    @Autowired
    private MaterailService materailService;
    @Autowired
    private ProductService productService;
    @FXML
    private TableView materialTable;
    @FXML
    private TextField queryTextField;
    @FXML
    private FlowPane productFlowPane;

    @FXML
    private VBox rootLayout;

    private ShortcutModel shortcutModel = new ShortcutModel();

    List<Button> buttons;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        materialTable.itemsProperty().bind(shortcutModel.materialsProperty());

        List<Product> products = productService.findAll();
        List<Button> buttons = products.stream().map(p -> {
            Button b = new Button();
            b.setOnAction(this::buttonClick);
            b.setMnemonicParsing(true);
            b.setText(p.getName());
            b.setUserData(p);
            return b;
        }).collect(Collectors.toList());
        this.buttons = buttons;
        productFlowPane.getChildren().setAll(buttons);
    }

    @Override
    public void beforeRouteIn(Scene scene) {
        Map<KeyCombination,Button> shortcuts = new HashMap<>();

        for (int j = 0; j < buttons.size(); j++) {
            int i = 97+j;
            Button btn = buttons.get(j);
            if(j==0){
                KeyCombination kc = new KeyCodeCombination(KeyCode.A,KeyCombination.CONTROL_DOWN);

                Mnemonic m = new Mnemonic(btn,kc);
                scene.addMnemonic(m);

            }
        }
        scene.setUserData(shortcuts);


    }

    @Override
    public void afterRouteOut(Scene scene) {

        scene.setUserData(null);
    }


    public void buttonClick(ActionEvent actionEvent) {

        queryData(actionEvent);
    }

    public void queryData(ActionEvent actionEvent) {
        String no = queryTextField.getText();
        search(no);
    }

    public void search(String no){
        List<Material> materials = materailService.findByNo(no);
        shortcutModel.setMaterials(FXCollections.observableList(materials));
    }


    public void shortcutKeyHandle(KeyEvent keyEvent) {
        System.out.println(keyEvent);
        productFlowPane.getChildren().forEach(btn ->{
            Product p = (Product) btn.getUserData();

            if(shouldFired(p.getControllDown()==1,p.getShiftDown()==1,p.getKeycode(),keyEvent)){
                ((Button)btn).fire();
            }
        });
    }

    private boolean shouldFired(boolean controllDown, boolean shiftDown, String keycode, KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode().getName()+"----------");
        if(controllDown != keyEvent.isControlDown()){
            return false;
        }
        if(shiftDown != keyEvent.isShiftDown()){
            return false;
        }

        if(!keyEvent.getCode().getName().equalsIgnoreCase(keycode)){
            return false;
        }
        return true;
    }


}
