package com.zz.crowd.mvc.handler;


import com.github.pagehelper.PageInfo;
import com.zz.crowd.entity.Admin;
import com.zz.crowd.entity.Role;
import com.zz.crowd.response.ResponseEntity;
import com.zz.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("role")
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    @RequestMapping("get/info")
    @ResponseBody
    public ResponseEntity<PageInfo> getPage(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){

        PageInfo<Role> pageInfo = roleService.getPageInfo(keyword, pageNum, pageSize);
        return ResponseEntity.successWithData(pageInfo);
    }


    @RequestMapping("save")
    @ResponseBody
    public ResponseEntity saveRole(@RequestParam("roleName") String roleName){
        if(StringUtils.isEmpty(roleName)){
            return ResponseEntity.errorWithMessage("角色名为空");
        }
        Role role = new Role();
        role.setName(roleName);
        roleService.save(role);
        return ResponseEntity.successWithData(null);
    }

    @RequestMapping("update")
    @ResponseBody
    public ResponseEntity updateRole(Role role){
        if(role == null){
            return ResponseEntity.errorWithMessage("更新为空");
        }
        roleService.update(role);
        return ResponseEntity.successWithData(null);
    }

    @RequestMapping("remove")
    @ResponseBody
    public ResponseEntity removeRoleByIds(@RequestBody List<Integer> ids){

        roleService.removeRoleByIds(ids);

        return ResponseEntity.successWithData(null);

    }
}
