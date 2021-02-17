package com.zz.crowd.mvc.config;

import com.zz.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class SecurityUser extends User {

    private Admin admin;

    public SecurityUser(Admin admin, List<GrantedAuthority> authorities){

        super(admin.getLoginAcct(), admin.getUserPwd(), authorities);

        this.admin = admin;

        //擦除密码信息，否则可以通过标签获取密码
        this.admin.setUserPwd(null);
    }

    public Admin getAdmin(){
        return this.admin;
    }
}
