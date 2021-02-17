package com.zz.crowd.mvc.config;


import com.google.gson.Gson;
import com.zz.crowd.exception.AccessForbiddenException;
import com.zz.crowd.exception.LoginAcctInUseException;
import com.zz.crowd.exception.LoginFailedException;
import com.zz.crowd.response.ResponseEntity;
import com.zz.crowd.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CrowdExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(CrowdExceptionResolver.class);

    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView loginFailedExceptionHandler(HttpServletRequest request, Exception exception){
        logger.error("request [{}] error [{}]", request.getContextPath(), exception);
        String viewName = "login-admin";
        return createModelAndView(exception, viewName);
    }

    @ExceptionHandler(AccessForbiddenException.class)
    public ModelAndView accessForbiddenExceptionHandler(HttpServletRequest request, Exception exception){
        logger.error("request [{}] error [{}]", request.getContextPath(), exception);
        String viewName = "login-admin";
        return createModelAndView(exception, viewName);
    }

    @ExceptionHandler(LoginAcctInUseException.class)
    public ModelAndView loginAcctInUseExceptionHandler(HttpServletRequest request, Exception exception){
        logger.error("request [{}] error [{}]", request.getContextPath(), exception);
        String viewName = "admin-add";
        return createModelAndView(exception, viewName);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response){
        logger.error("request [{}] error [{}]", request.getContextPath(), exception);
        if(CrowdUtil.judgeRequestType(request)){
            //json
            Gson gson = new Gson();
            try {
                response.getWriter().write(gson.toJson(ResponseEntity.errorWithMessage(exception.getMessage())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        String viewName = "error";
        return createModelAndView(exception, viewName);
    }

    private ModelAndView createModelAndView(Exception exception, String viewName){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
