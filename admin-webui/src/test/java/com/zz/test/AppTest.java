package com.zz.test;


import com.zz.crowd.entity.Admin;
import com.zz.crowd.entity.Role;
import com.zz.crowd.mapper.AdminMapper;
import com.zz.crowd.mapper.RoleMapper;
import com.zz.crowd.service.AdminService;
import com.zz.crowd.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml", "classpath:spring-tx.xml"})
public class AppTest {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Test
    public void insertAdmin(){
        Admin admin = new Admin();
        for (int i = 0; i<20;i++){
            admin.setLoginAcct("zzj" + i);
            admin.setUserPwd("123456" + i);
            admin.setEmail("110@qqq.com");
            adminMapper.insertSelective(admin);
        }

    }

    @Test
    public void insertAdminService(){
        Admin admin = new Admin();
        admin.setLoginAcct("zzj212");
        admin.setUserPwd("123456");
        admin.setEmail("110@qqq.com");
        adminService.saveAdmin(admin);
    }

    @Test
    public void getAdmin(){
        Admin admin = adminMapper.selectByPrimaryKey(3);
        logger.info("admin [{}]", admin);
    }

    @Test
    public void addRoles(){
        for (int i = 0; i < 100 ; i++){
            roleMapper.insertSelective(new Role(null, "role" + i));
        }
    }
}
