package com.zhw.mes.ui.login;

import com.zhw.mes.support.controller.RouteCallbackController;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogoutController extends RouteCallbackController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init.........");

    }

    @Override
    public void beforeRouteIn(Scene scene) {
        System.out.println("开始进入本页面,scene = 注销" + scene);
    }

    @Override
    public void afterRouteOut(Scene scene) {
        System.out.println("开始离开本页面,scene = 注销" + scene);
    }
}
