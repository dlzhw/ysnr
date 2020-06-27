package com.zhw.mes.support.route;

import com.zhw.mes.support.controller.RouteCallbackController;
import com.zhw.mes.support.utils.AppUtil;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StageAppRouter implements AppRouter {
    @Override
    public void route(Route route) {
        Stage stage = new Stage();
        Pane nextNode = AppUtil.loadFXML(route.getFxmlPath());
        Scene scene = new Scene(nextNode);

        Object controller = AppUtil.fetchController(nextNode);
        if(controller instanceof RouteCallbackController){
            //显示窗口前调用路由回调方法
            ((RouteCallbackController) controller).beforeRouteIn(scene);
            //关闭窗口前,调用路由回调方法
            stage.setOnCloseRequest((e)-> ((RouteCallbackController) controller).afterRouteOut(scene));
        }

        stage.setTitle(route.getTitle());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
