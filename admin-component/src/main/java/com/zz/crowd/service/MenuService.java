package com.zz.crowd.service;

import com.zz.crowd.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAll();

    void save(Menu menu);

    void update(Menu menu);

    void remove(Integer id);
}
