package com.zhw.mes.ui.main;

import com.zhw.mes.domain.Menu;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public class IndexModel {
    public ObservableList<Menu> getMenus() {
        return menus.get();
    }

    public ListProperty<Menu> menusProperty() {
        return menus;
    }

    public void setMenus(ObservableList<Menu> menus) {
        this.menus.set(menus);
    }

    private ListProperty<Menu> menus = new SimpleListProperty<>(FXCollections.observableArrayList());
}
