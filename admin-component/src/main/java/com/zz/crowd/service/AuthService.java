package com.zz.crowd.service;

import com.zz.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAuthByRoleId(Integer roleId);

    void doRoleAssign(Map<String, List<Integer>> map);

    List<String> selectAuthByAdminId(Integer id);
}
