package com.zz.crowd.service.impl;

import com.zz.crowd.entity.Menu;
import com.zz.crowd.entity.MenuExample;
import com.zz.crowd.mapper.MenuMapper;
import com.zz.crowd.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void save(Menu menu) {
        menuMapper.insertSelective(menu);
    }

    @Override
    public void update(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public void remove(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
