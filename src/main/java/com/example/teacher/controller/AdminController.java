package com.example.teacher.controller;

import com.example.teacher.pojo.Admin;
import com.example.teacher.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author
 * @email
 * @description 管理员controller
 */

@Controller
@RequestMapping("admin")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpSession session;

    // 管理登录
    @PostMapping("/login")
    public String login(@Validated Admin admin, Model model) {

        Admin adminFromDB = adminService.login(admin);

        if (adminFromDB == null) {
            session.setAttribute("login_msg", "邮箱或密码错误");
            return "redirect:/";
        }
        session.setAttribute("admin", adminFromDB);
        session.removeAttribute("login_msg");
        return "redirect:/admins/index";

    }

    // 登出
    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("admin");
        return "redirect:/";
    }

    // 修改密码
    @PostMapping("/password")
    public String changePassword(@NotNull(message = "原密码不能为空")
                                 @RequestParam String password,
                                 @NotNull(message = "新密码不能为空")
                                 @RequestParam String password2) {

        Admin admin = getAdminFromSession();

        adminService.changePassword(password, password2, admin);


        session.invalidate();

        return "redirect:/";
    }


    public Admin getAdminFromSession() {
        Admin adminFromSession = (Admin) session.getAttribute("admin");
        if (adminFromSession == null) {
            throw new RuntimeException("请先登录");
        }
        return adminFromSession;
    }


}
