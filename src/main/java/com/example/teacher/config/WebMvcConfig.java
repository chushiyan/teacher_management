package com.example.teacher.config;

import com.example.teacher.interceptor.AdminLoginInterceptor;
import com.example.teacher.interceptor.TeacherLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author
 * @email
 * @description 配置登录拦截、资源路径映射
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${file.uploadPath}")
    private String uploadPath;


    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;


    @Autowired
    private TeacherLoginInterceptor teacherLoginInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admins/**")
                .addPathPatterns("/admin/**")
                .addPathPatterns("/admins/**/**")
                .excludePathPatterns("/admin/login");

        registry.addInterceptor(teacherLoginInterceptor)
                .addPathPatterns("/teachers/**")
                .addPathPatterns("/teacher/**")
                .addPathPatterns("/teachers/**/**")
                .excludePathPatterns("/teacher/login");

    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/",
                        "classpath:/static/",
                        "classpath:/public/");
        registry.addResourceHandler("/img/**").
                addResourceLocations("file:///" + uploadPath);
    }
}
