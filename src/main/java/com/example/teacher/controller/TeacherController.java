package com.example.teacher.controller;

import com.example.teacher.pojo.Admin;
import com.example.teacher.pojo.Teacher;
import com.example.teacher.service.TeacherService;
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

/**
 * @author
 * @email
 * @description 管理员controller
 */

@Controller
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private HttpSession session;



    /**
     *  教师登录
     * @param teacher 页面提交的 teacher对象
     * @param model 模型对象
     * @return 返回ftl模板。根据情况，回到回到登录页（也是欢迎页）、教师主页、教师首次登录修改密码页
     */
    @PostMapping("/login")
    public String login(@Validated Teacher teacher, Model model) {

        Teacher teacherFromDB = teacherService.login(teacher);

        // 如果没有查询到教师
        if (teacherFromDB == null) {
            session.setAttribute("login_msg", "邮箱或密码错误");
            return "redirect:/";
        }

        // 保存登录信息
        session.setAttribute("teacher", teacherFromDB);

        // 如果教师状态值为-1（未激活状态），跳转到 修改密码页面
        if (teacherFromDB.getStatus() == -1) {
            return "redirect:/password";
        }

        // 移除可能存在的login_msg，避免错误的提示
        session.removeAttribute("login_msg");

        // 重定向到教师主页
        return "redirect:/teachers/index";
    }


    /**
     *  教师：首次登录修改密码
     * @param password 原密码
     * @param password2 新密码
     * @return 返回ftl模板。回到登录页（也是欢迎页）
     */
    @PostMapping("/reset")
    public String reset(@NotNull(message = "原密码不能为空")
                                 @RequestParam String password,
                                 @NotNull(message = "新密码不能为空")
                                 @RequestParam String password2) {

        Teacher teacher = getTeacherFromSession();

        teacherService.resetPassword(password, password2, teacher);

        session.invalidate();

        return "redirect:/";
    }



    /**
     *  教师登出
     * @return 返回ftl模板。回到登录页（也是欢迎页）
     */
    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("teacher");
        return "redirect:/";
    }

    /**
     *  从session中获取已经保存的 teacher 对象
     * @return
     */
    public Teacher getTeacherFromSession() {
        Teacher teacherFromSession = (Teacher) session.getAttribute("teacher");
        if (teacherFromSession == null) {
            throw new RuntimeException("请先登录");
        }
        return teacherFromSession;
    }



    /**
     *  教师：修改密码
     * @param password 原密码
     * @param password2 新密码
     * @return 返回ftl模板。回到登录页（也是欢迎页）
     */
    @PostMapping("/password")
    public String changePassword(@NotNull(message = "原密码不能为空")
                                 @RequestParam String password,
                                 @NotNull(message = "新密码不能为空")
                                 @RequestParam String password2) {

        Teacher teacher = getTeacherFromSession();

        teacherService.changePassword(password, password2, teacher);

        session.invalidate();

        return "redirect:/";
    }



    /**
     *  教师：修改个人资料
     * @param teacher 页面提交的 teacher 对象
     * @return 返回ftl模板。回到/teachers/profile  教师个人资料页
     */
    @PostMapping("/update")
    public String update(Teacher teacher) {

        Teacher teacherFromSession = getTeacherFromSession();

        teacherFromSession.setName(teacher.getName());

        teacherFromSession.setEmail(teacher.getEmail());

        teacherFromSession.setPhone(teacher.getPhone());

        teacherFromSession.setAge(teacher.getAge());

        teacherFromSession.setGender(teacher.getGender());

        teacherFromSession.setBirthplace(teacher.getBirthplace());

        teacherFromSession.setIdNumber(teacher.getIdNumber());

        teacherFromSession.setGraduateSchool(teacher.getGraduateSchool());

        teacherFromSession.setEducation(teacher.getEducation());

        teacherFromSession.setDepartment(teacher.getDepartment());

        teacherService.update(teacherFromSession);

        session.setAttribute("teacher", teacherFromSession);

        return "redirect:/teachers/profile";
    }


}
