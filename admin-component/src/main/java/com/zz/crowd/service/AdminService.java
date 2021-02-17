package com.zz.crowd.service;

import com.github.pagehelper.PageInfo;
import com.zz.crowd.entity.Admin;

import java.util.List;

public interface AdminService {
    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPwd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    boolean remove(Integer id);

    Admin getById(Integer id);

    int updateAdmin(Admin admin);

    void removeAdminId(Integer adminId);

    void insertRelationShip(Integer adminId, List<Integer> roleIdList);

    Admin getAdminByLoginAcct(String username);
}
