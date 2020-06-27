package com.zhw.mes.support.route;

/**
 * 封装路由信息
 */
public class Route {
    /**
     * 页面地址
     */
    private String fxmlPath;
    /**
     * 页面标题
     */
    private String title;

    public String getFxmlPath() {
        return fxmlPath;
    }

    public void setFxmlPath(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
