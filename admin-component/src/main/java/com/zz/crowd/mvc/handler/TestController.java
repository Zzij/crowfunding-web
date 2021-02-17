package com.zz.crowd.mvc.handler;

import com.zz.crowd.entity.Admin;
import com.zz.crowd.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private AdminService adminService;

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test/ssm")
    public String testSsm(ModelMap modelMap){
        List<Admin> admins = adminService.getAll();
        modelMap.addAttribute("admins", admins);
        return "listadmin";
    }

    @RequestMapping("/test/send")
    @ResponseBody
    public String testSend(@RequestParam("array[]") List<Integer> list){
        list.forEach(number -> logger.info("arrays: {}", number));
        return "success";
    }

    @RequestMapping("/test/error")
    public String testError(){
        System.out.println(10 / 0);
        return "login-admin";
    }

    @RequestMapping("/test/send2")
    @ResponseBody
    public String testSend2(@RequestBody List<Integer> list){
        list.forEach(number -> logger.info("arrays: {}", number));
        return "success";
    }
}
