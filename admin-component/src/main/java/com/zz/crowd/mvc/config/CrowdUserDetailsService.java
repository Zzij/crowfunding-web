package com.zz.crowd.mvc.config;

import com.zz.crowd.entity.Admin;
import com.zz.crowd.entity.Role;
import com.zz.crowd.service.AdminService;
import com.zz.crowd.service.AuthService;
import com.zz.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByLoginAcct(username);

        Integer adminId = admin.getId();

        List<Role> roles = roleService.getAssignRole(adminId);

        List<String> auths = authService.selectAuthByAdminId(adminId);

        ArrayList<GrantedAuthority> authorities = new ArrayList<>();

        roles.forEach(role -> {

            //注意spring security的角色要加前缀 以此区别role和auth
            String roleName = "ROLE_" + role.getName();

            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);

            authorities.add(simpleGrantedAuthority);

        });

        auths.forEach(auth -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(auth);
            authorities.add(simpleGrantedAuthority);
        });

        return new SecurityUser(admin, authorities);
    }
}
