package com.zz.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zz.crowd.CrowdConstant;
import com.zz.crowd.entity.Admin;
import com.zz.crowd.entity.AdminExample;
import com.zz.crowd.exception.LoginAcctInUseException;
import com.zz.crowd.exception.LoginFailedException;
import com.zz.crowd.mapper.AdminMapper;
import com.zz.crowd.service.AdminService;
import com.zz.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void saveAdmin(Admin admin) {
        if(admin == null){
            return;
        }
        //String md5 = CrowdUtil.md5(admin.getUserPwd());
        String md5 = passwordEncoder.encode(admin.getUserPwd());
        admin.setUserPwd(md5);
        try {
            adminMapper.insertSelective(admin);
        }catch (DuplicateKeyException e){
            throw new LoginAcctInUseException("用户已存在");
        }

    }

    public List<Admin> getAll() {
        List<Admin> admins = adminMapper.selectByExample(new AdminExample());
        return admins;
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPwd) {
        if(loginAcct == null || loginAcct.length() == 0){
            throw new LoginFailedException("登录名不能为空");
        }

        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if(admins == null || admins.isEmpty() || admins.size() > 1){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        Admin admin = admins.get(0);
        if(admin == null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        String userPwdForm = CrowdUtil.md5(userPwd);

        if(!Objects.equals(userPwdForm, admin.getUserPwd())){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        List<Admin> admins = adminMapper.selectByKeyword(keyword);

        return new PageInfo<>(admins);
    }

    @Override
    public boolean remove(Integer id) {
        return adminMapper.deleteByPrimaryKey(id) == 1;
    }

    @Override
    public Admin getById(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public int updateAdmin(Admin admin) {
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        }catch (Exception e){
            throw new LoginAcctInUseException("该用户名已存在，修改失败");
        }
        return 1;
    }

    @Override
    public void removeAdminId(Integer adminId) {
        adminMapper.deleteByAdminId(adminId);
    }

    @Override
    public void insertRelationShip(Integer adminId, List<Integer> roleIdList) {
        adminMapper.insertRelationShip(adminId, roleIdList);
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(username);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if(admins == null || admins.isEmpty()){
            return null;
        }
        return admins.get(0);
    }
}
