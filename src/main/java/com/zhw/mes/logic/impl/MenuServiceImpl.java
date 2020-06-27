package com.zhw.mes.logic.impl;

import com.zhw.mes.domain.Menu;
import com.zhw.mes.logic.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    private List<Menu> data = new ArrayList<>();
    {
        Menu m = new Menu();
        m.setDisplayName("物料入库");
        m.setRoutePath("/ui/login.fxml");
        data.add(m);
        m = new Menu();
        m.setDisplayName("物料出库");
        m.setRoutePath("/ui/logout.fxml");
        data.add(m);
        m = new Menu();
        m.setDisplayName("demo");
        m.setRoutePath("/ui/demo.fxml");
        data.add(m);
    }
    @Override
    public List<Menu> findMenus() {
        return data;
    }
}
