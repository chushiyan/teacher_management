package com.example.teacher.interceptor;

import com.example.teacher.pojo.Admin;
import com.example.teacher.pojo.Teacher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author
 * @email
 * @description 登录拦截器
 */
@Component
public class TeacherLoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("---- 进入教师登录拦截器 ----");

        //  得到对象
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");

        //  判断对象是否存在
        if (teacher != null) {
            return true;
        } else {
            //  不存在则跳转到登录页
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }


    }


}
