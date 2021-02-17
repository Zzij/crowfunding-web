package com.zz.crowd.service;

import com.github.pagehelper.PageInfo;
import com.zz.crowd.entity.Role;

import java.util.List;

public interface RoleService {

    PageInfo<Role> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void save(Role role);

    void update(Role role);

    void removeRoleByIds(List<Integer> ids);

    List<Role> getAssignRole(Integer adminId);

    List<Role> getUnAssignRole(Integer adminId);
}
