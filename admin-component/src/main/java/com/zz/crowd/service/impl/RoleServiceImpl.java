package com.zz.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zz.crowd.entity.Role;
import com.zz.crowd.entity.RoleExample;
import com.zz.crowd.mapper.RoleMapper;
import com.zz.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = roleMapper.selectByKeyword(keyword);
        return new PageInfo<>(roles);
    }

    @Override
    public void save(Role role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public void update(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void removeRoleByIds(List<Integer> ids) {

        RoleExample roleExample = new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(ids);

        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignRole(Integer adminId) {

        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssignRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }
}
