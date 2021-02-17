package com.zz.crowd.service.impl;

import com.zz.crowd.entity.Auth;
import com.zz.crowd.entity.AuthExample;
import com.zz.crowd.mapper.AuthMapper;
import com.zz.crowd.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {

        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAuthByRoleId(Integer roleId) {
        return authMapper.selectAuthByRoleId(roleId);
    }

    @Override
    public void doRoleAssign(Map<String, List<Integer>> map) {
        Integer roleId = map.get("roleId").get(0);
        authMapper.deleteByRoleId(roleId);
        List<Integer> authIdArray = map.get("authIdArray");
        if(authIdArray != null && !authIdArray.isEmpty()){
            authMapper.insertRoleIdAndAuthId(roleId, authIdArray);
        }

    }

    @Override
    public List<String> selectAuthByAdminId(Integer id) {

        return authMapper.selectAuthByAdminId(id);
    }
}
