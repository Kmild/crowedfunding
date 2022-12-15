package com.xiaoyan.crowd.service;

import com.xiaoyan.crowd.mvc.pojo.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenuById(Menu menu);

    void removeMenuById(Integer id);
}
