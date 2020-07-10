package com.example.teacher.controller;

import com.example.teacher.pojo.Promote;
import com.example.teacher.pojo.Teacher;
import com.example.teacher.service.PromoteService;
import com.example.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author
 * @email
 * @description 页面映射
 */
@Controller
public class PageController {

    @Autowired
    private TeacherService teacherService;


    @Autowired
    private PromoteService promoteService;


    // 首页映射
    @RequestMapping("/")
    public String index() {
        return "login";
    }


    // 教师首次登录修改密码页面
    @RequestMapping("/password")
    public String password() {
        return "password";
    }

    // 教师首次登录修改密码页面
    @RequestMapping("/404")
    public String error() {
        return "404";
    }


    @RequestMapping("/admins/{page}")
    public String path(@PathVariable String page) {
        return "/admins/" + page;
    }

    @RequestMapping("/admins/examine/add")
    public String adminsExamineAdd(Model model) {

        List<Teacher> teacherList = teacherService.findAll();

        model.addAttribute("teacherList", teacherList);

        return "/admins/examine/add";
    }


    @RequestMapping("/admins/{path}/{page}")
    public String admins(@PathVariable String path, @PathVariable String page) {
        return "/admins/" + path + "/" + page;
    }


    @RequestMapping("/teachers/{page}")
    public String teachers(@PathVariable String page) {
        return "/teachers/" + page;
    }


    @RequestMapping("/teachers/application/add")
    public String teachersExamineAdd(Model model) {

        List<Promote> promoteList = promoteService.findAll();

        model.addAttribute("promoteList", promoteList);

        return "/teachers/application/add";
    }

    @RequestMapping("/teachers/{path}/{page}")
    public String teachers(@PathVariable String path, @PathVariable String page) {
        return "/teachers/" + path + "/" + page;
    }

}
