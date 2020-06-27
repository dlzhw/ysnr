package com.zhw.mes.ui.demo;

import com.zhw.mes.domain.Material;
import com.zhw.mes.domain.Product;
import com.zhw.mes.logic.MaterailService;
import com.zhw.mes.logic.ProductService;
import com.zhw.mes.support.controller.RouteCallbackController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class DemoController extends RouteCallbackController implements Initializable {
    private final Logger logger = LoggerFactory.getLogger(DemoController.class);

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

    private DemoModel demoModel = new DemoModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        materialTable.itemsProperty().bind(demoModel.materialsProperty());
        List<Product> products = productService.findAll();
        List<Button> buttons = products.stream().map(p -> {
            Button b = new Button();
            b.setOnAction(this::buttonClick);
            b.setText(p.getName());
            b.setUserData(p);
            return b;
        }).collect(Collectors.toList());
        Button b = buttons.get(0);

        productFlowPane.getChildren().setAll(buttons);
    }

    @Override
    public void beforeRouteIn(Scene scene) {
        super.beforeRouteIn(scene);
    }

    @Override
    public void afterRouteOut(Scene scene) {
        super.afterRouteOut(scene);
    }


    private void buttonClick(ActionEvent actionEvent) {
        Button btn = (Button) actionEvent.getSource();
        Product p = (Product) btn.getUserData();
        queryTextField.setText(p.getMno());
        search(p.getMno());
    }

    public void queryData(ActionEvent actionEvent) {
        String no = queryTextField.getText();
        search(no);
    }

    public void search(String no){
        List<Material> materials = materailService.findByNo(no);
        demoModel.setMaterials(FXCollections.observableList(materials));
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
