package com.example.teacher.controller;

import com.example.teacher.pojo.Application;
import com.example.teacher.pojo.Application;
import com.example.teacher.pojo.Teacher;
import com.example.teacher.service.ApplicationService;
import com.example.teacher.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;

/**
 * @author
 * @email
 * @description
 */
@Controller
@RequestMapping("application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;


    // 管理员：单条件分页查询申请表
    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestParam int page,
                       @RequestParam int limit,
                       @RequestParam(required = false) String query) {

        Page<Application> applicationPage = applicationService.list(page, limit, query);

        return new Result(200, "查询成功",
                applicationPage.getTotalElements(),
                applicationPage.getContent());

    }


    /**
     * 教师：增加
     *
     * @param promoteId 页面提交的晋升Id
     * @return 自定义的Result返回结果类对象
     */
    @PostMapping("/apply/{promoteId}")
    @ResponseBody
    public Result add(@PathVariable String promoteId) {

        Teacher teacher = this.getTeacherFromSession();

        applicationService.add(promoteId, teacher);

        return new Result(200, "增加成功");
    }


    /**
     * 根据 id 逻辑删除
     *
     * @param applicationId 页面提交的id
     * @return 自定义的Result返回结果类对象
     */
    @DeleteMapping("/{applicationId}")
    @ResponseBody
    public Result deleteById(@PathVariable String applicationId) {
        applicationService.deleteById(0, applicationId);
        return new Result(200, "删除成功");
    }


    /**
     * 管理员：根据 id 审核
     *
     * @param applicationId 页面提交的id
     * @return 自定义的Result返回结果类对象
     */
    @PostMapping("/check/{applicationId}")
    @ResponseBody
    public Result checkById(@PathVariable String applicationId) {
        applicationService.deleteById(1, applicationId);
        return new Result(200, "审核成功");
    }

    /**
     * 管理员：根据 id 不通过申请
     *
     * @param applicationId 页面提交的id
     * @return 自定义的Result返回结果类对象
     */
    @PostMapping("/skip/{applicationId}")
    @ResponseBody
    public Result skipById(@PathVariable String applicationId) {
        applicationService.deleteById(2, applicationId);
        return new Result(200, "审核不通过成功");
    }


    /**
     * 修改
     *
     * @param application 页面提交的application对象
     * @return 自定义的Result返回结果类对象
     */
//    @PutMapping("/{applicationId}")
//    @PostMapping("/update/{applicationId}")
//    @ResponseBody
//    public Result update(@Validated Application application, @PathVariable String applicationId) {
//        application.setApplicationId(applicationId);
//        applicationService.update(application);
//        return new Result(200, "修改成功");
//    }


    @Autowired
    private HttpSession session;

    private Teacher getTeacherFromSession() {

        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            throw new RuntimeException("请先登录");
        }
        return teacher;
    }


    // 教师：分页查询自己的申请
    @RequestMapping("/mine")
    @ResponseBody
    public Result mineList(@RequestParam int page,
                           @RequestParam int limit) {

        Teacher teacher = this.getTeacherFromSession();

        Page<Application> applicationPage = applicationService.mine(page, limit, teacher.getTeacherId());

        return new Result(200, "查询成功",
                applicationPage.getTotalElements(),
                applicationPage.getContent());

    }


}
