package com.zz.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.zz.crowd.CrowdConstant;
import com.zz.crowd.entity.Admin;
import com.zz.crowd.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 管理员登陆请求处理
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private static Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("/do/login")
    public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPwd") String userPwd, HttpSession session){
        logger.info("login loginAcct [{}], userPwd [{}]", loginAcct, userPwd);
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPwd);
        session.setAttribute(CrowdConstant.LOGIN_ADMIN, admin);
        return "redirect:/admin/do/main/page";
    }

    @RequestMapping("/do/loginout")
    public String doLogin(HttpSession session){
        session.invalidate();
        // 重定向 不用再次提交表单
        return "redirect:/admin/do/login/page";
    }

    @PreAuthorize("hasRole('经理')")
    @RequestMapping("get/page")
    public String getPage(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                          ModelMap modelMap){

        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute("pageInfo", pageInfo);
        return "admin-page";
    }

    @RequestMapping({"do/removeadmin/{id}/{pageNum}/{keyword}", "do/removeadmin/{id}/{pageNum}"})
    public String removeAdminById(@PathVariable("id") Integer id,
                                  @PathVariable("pageNum") Integer pageNum,
                                  @PathVariable(value = "keyword", required = false) String keyword){
        adminService.remove(id);

        //删除完成 跳转的页面
        if(keyword == null){
            return "redirect:/admin/get/page?pageNum=" + pageNum;
        }
        return "redirect:/admin/get/page?pageNum=" + pageNum + "&keyword=" + keyword;
    }


    @RequestMapping("/do/add")
    public String doAdd(Admin admin){
        logger.info("add admin [{}]", admin);
        adminService.saveAdmin(admin);
        return "redirect:/admin/do/main/page";
    }

    @RequestMapping({"do/edit/page"})
    public String getAdminById(@RequestParam(value = "id") Integer id,
                               @RequestParam(value = "keyword", defaultValue = "") String keyword,
                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               ModelMap modelMap){
        Admin admin = adminService.getById(id);
        modelMap.addAttribute("admin", admin);
        return "admin-edit";
    }


    @RequestMapping("do/edit")
    public String updateAdmin(Admin admin,
                              @RequestParam(value = "keyword", defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               ModelMap modelMap){
        adminService.updateAdmin(admin);
        if(keyword == null){
            return "redirect:/admin/get/page?pageNum=" + pageNum;
        }
        return "redirect:/admin/get/page?pageNum=" + pageNum + "&keyword=" + keyword;
    }
}
