package com.zhw.mes.ui.main;

import com.zhw.mes.logic.MenuService;
import com.zhw.mes.support.route.AppRouter;
import com.zhw.mes.support.utils.AppUtil;
import com.zhw.mes.support.utils.AppUtilImpl;
import com.zhw.mes.support.route.Route;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IndexController implements Initializable {
    @Autowired
    private MenuService menuService;
    @Autowired
    private AppRouter appRouter;
    @Autowired
    private AppUtil appUtil;

    @Autowired
    private IndexModel indexModel;


    public MenuBar menuBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indexModel.menusProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    menuBar.getMenus().clear();
                    newValue.forEach(m -> {
                        Menu menu = new Menu();
                        menu.setText(m.getDisplayName());
                        MenuItem menuItem = new MenuItem(m.getDisplayName());
                        menuItem.setOnAction(this::handleRoute);
                        menuItem.setUserData(m);
                        menu.getItems().add(menuItem);
                        menuBar.getMenus().add(menu);
                    });
                })
        );


        indexModel.menusProperty().addAll(menuService.findMenus());

    }

    public void handleRoute(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if(source instanceof MenuItem){
            com.zhw.mes.domain.Menu menu = (com.zhw.mes.domain.Menu) ((MenuItem) source).getUserData();
            Route route = new Route();
            route.setTitle(menu.getDisplayName());
            route.setFxmlPath(menu.getRoutePath());
            appRouter.route(route);
        }
    }
}
