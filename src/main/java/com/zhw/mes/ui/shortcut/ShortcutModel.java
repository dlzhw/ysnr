package com.zhw.mes.ui.shortcut;

import com.zhw.mes.domain.Material;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

public class ShortcutModel {
    private SimpleListProperty<Material> materials = new SimpleListProperty<>();
    private SimpleListProperty<Button> products = new SimpleListProperty<>();

    public ObservableList<Material> getMaterials() {
        return materials.get();
    }

    public SimpleListProperty<Material> materialsProperty() {
        return materials;
    }

    public void setMaterials(ObservableList<Material> materials) {
        this.materials.set(materials);
    }

    public ObservableList<Button> getProducts() {
        return products.get();
    }

    public SimpleListProperty<Button> productsProperty() {
        return products;
    }

    public void setProducts(ObservableList<Button> products) {
        this.products.set(products);
    }
}
