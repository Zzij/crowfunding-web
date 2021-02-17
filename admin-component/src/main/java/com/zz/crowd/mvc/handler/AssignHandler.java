package com.zz.crowd.mvc.handler;


import com.zz.crowd.entity.Auth;
import com.zz.crowd.entity.Role;
import com.zz.crowd.response.ResponseEntity;
import com.zz.crowd.service.AdminService;
import com.zz.crowd.service.AuthService;
import com.zz.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("assign")
public class AssignHandler {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @RequestMapping("to/assign/role/page")
    public String assignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap){

        //查询已分配的角色
        List<Role> assignRoles = roleService.getAssignRole(adminId);


        //查询未分配的角色
        List<Role> unAssignRoles = roleService.getUnAssignRole(adminId);

        modelMap.addAttribute("assignRoles", assignRoles);
        modelMap.addAttribute("unAssignRoles", unAssignRoles);

        return "assign-role";
    }

    @RequestMapping("do/role/assign/page")
    public String doAssignRolePage(@RequestParam("adminId") Integer adminId,
                                   @RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("keyword") String keyword,
                                   @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList){
        adminService.removeAdminId(adminId);
        if(roleIdList == null || roleIdList.isEmpty()){
            return "redirect:/admin/get/page?pageNum=" + pageNum + "&keyword=" + keyword;
        }

        adminService.insertRelationShip(adminId, roleIdList);

        if(pageNum == null || keyword == null){
            return "redirect:/admin/get/page";
        }

        if(pageNum != null && keyword != null){
            return "redirect:/admin/get/page?pageNum=" + pageNum + "&keyword=" + keyword;
        }

        if(pageNum != null){
            return "redirect:/admin/get/page?pageNum=" + pageNum;
        }

        if(keyword != null){
            return "redirect:/admin/get/page?keyword=" + keyword;
        }

        return "redirect:/admin/get/page?pageNum=" + pageNum + "&keyword=" + keyword;
    }


    @RequestMapping("get/all/auth")
    @ResponseBody
    public ResponseEntity getAllAuth(){

        List<Auth> authList =  authService.getAll();
        return ResponseEntity.successWithData(authList);

    }


    @ResponseBody
    @RequestMapping("get/auth/role/id")
    public ResponseEntity getAuthByRoleId(@RequestParam("roleId") Integer roleId){

        List<Integer> auths = authService.getAuthByRoleId(roleId);
        return ResponseEntity.successWithData(auths);
    }

    @RequestMapping("do/role/assign")
    @ResponseBody
    public ResponseEntity doRoleAssign(@RequestBody Map<String, List<Integer>> map){
        authService.doRoleAssign(map);
        return ResponseEntity.successWithData(null);
    }
}
