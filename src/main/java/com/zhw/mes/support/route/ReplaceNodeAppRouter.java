package com.zhw.mes.support.route;

import com.zhw.mes.support.controller.RouteCallbackController;
import com.zhw.mes.support.utils.AppUtil;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ReplaceNodeAppRouter implements AppRouter {
    @Override
    public void route(Route route) {

        Pane nextNode = AppUtil.loadFXML(route.getFxmlPath());
        Scene scene = AppUtil.getAppRootScene();


        Pane mainContentNode = (Pane) AppUtil.getAppMainContentNode();
        Pane preNode = null;
        int childSize = mainContentNode.getChildren().size();
        if (childSize > 1){
            preNode = (Pane) mainContentNode.getChildren().remove(childSize-1);
        }

        if(preNode!=null){
            Object preController = AppUtil.fetchController(preNode);
            if(preController instanceof RouteCallbackController){
                ((RouteCallbackController) preController).afterRouteOut(scene);
            }
        }

        if(nextNode!=null){
            Object nextController = AppUtil.fetchController(nextNode);
            if(nextController instanceof RouteCallbackController){
                ((RouteCallbackController) nextController).beforeRouteIn(scene);
            }
        }

        mainContentNode.getChildren().add(nextNode);
    }
}
