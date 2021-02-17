package com.zz.crowd.mvc.interceptor;

import com.zz.crowd.CrowdConstant;
import com.zz.crowd.entity.Admin;
import com.zz.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * web拦截器
 * 拦截页面请求
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(CrowdConstant.LOGIN_ADMIN);

        if(admin == null){
            throw new AccessForbiddenException(CrowdConstant.ACCESS_FORBIDDEN_MESSAGE);
        }
        return super.preHandle(request, response, handler);
    }
}
