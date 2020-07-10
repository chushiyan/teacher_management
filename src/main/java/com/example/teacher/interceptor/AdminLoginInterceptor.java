package com.example.teacher.interceptor;

import com.example.teacher.pojo.Admin;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author
 * @email
 * @description 登录拦截器
 */
@Component
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("---- 进入管理员拦截器 ----");

        //  得到对象
        Admin admin = (Admin) request.getSession().getAttribute("admin");

        //  判断对象是否存在
        if (admin != null) {
            return true;
        } else {
            //  不存在则跳转到登录页
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }


    }


}
