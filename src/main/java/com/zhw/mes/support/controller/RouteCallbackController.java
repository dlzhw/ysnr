package com.zhw.mes.support.controller;

import javafx.scene.Scene;

/**
 * 支持路由回调的Controller
 */
public abstract class RouteCallbackController {
    /**
     * 路由进入本页面前,调用
     * @param scene
     */
    public void beforeRouteIn(Scene scene){}

    /**
     * 路由离开本页面后,调用
     * @param scene
     */
    public void afterRouteOut(Scene scene){}
}
