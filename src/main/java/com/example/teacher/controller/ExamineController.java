package com.example.teacher.controller;

import com.example.teacher.pojo.Application;
import com.example.teacher.pojo.Examine;
import com.example.teacher.pojo.Examine;
import com.example.teacher.pojo.Teacher;
import com.example.teacher.service.ExamineService;
import com.example.teacher.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author
 * @email
 * @description
 */
@Controller
@RequestMapping("examine")
public class ExamineController {

    @Autowired
    private ExamineService examineService;

    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestParam int page,
                       @RequestParam int limit,
                       @RequestParam(required = false) String query) {
        Page<Examine> examinePage = examineService.list(page, limit, query);

        return new Result(200, "查询成功",
                examinePage.getTotalElements(),
                examinePage.getContent());

    }

    @Autowired
    private HttpSession session;

    private Teacher getTeacherFromSession() {
        Teacher teacher = (Teacher) session.getAttribute("teacher");
        if (teacher == null) {
            throw new RuntimeException("请先登录");
        }
        return teacher;
    }

    // 教师：分页查询自己的考核
    @RequestMapping("/mine")
    @ResponseBody
    public Result mineList(@RequestParam int page,
                           @RequestParam int limit) {

        Teacher teacher = this.getTeacherFromSession();

        Page<Examine> examinePage = examineService.mine(page, limit, teacher.getTeacherId());

        return new Result(200,
                "查询成功",
                examinePage.getTotalElements(),
                examinePage.getContent());

    }


    /**
     * 管理员：增加
     *
     * @param examine 页面提交的 examine 对象
     * @return 自定义的Result返回结果类对象
     */
    @PostMapping("/{teacherId}")
    @ResponseBody
    public Result add(@Validated Examine examine, @PathVariable String teacherId) {
        examineService.add(examine, teacherId);
        return new Result(200, "增加成功");
    }


    /**
     * 管理员：根据 id 逻辑删除
     *
     * @param examineId 页面提交的id
     * @return 自定义的Result返回结果类对象
     */
    @DeleteMapping("/{examineId}")
    @ResponseBody
    public Result deleteById(@PathVariable String examineId) {
        examineService.deleteById(0, examineId);
        return new Result(200, "删除成功");
    }


    /**
     * 管理员：根据 id 开始考核（修改status值）
     *
     * @param examineId 页面提交的id
     * @return 自定义的Result返回结果类对象
     */
    @DeleteMapping("/start/{examineId}")
    @ResponseBody
    public Result startById(@PathVariable String examineId) {
        examineService.deleteById(2, examineId);
        return new Result(200, "操作成功");
    }


    /**
     * 管理员：根据 id 完成考核（修改status值）
     *
     * @param examineId 页面提交的id
     * @return 自定义的Result返回结果类对象
     */
    @DeleteMapping("/finish/{examineId}")
    @ResponseBody
    public Result finishById(@PathVariable String examineId) {
        examineService.deleteById(3, examineId);
        return new Result(200, "操作成功");
    }


    /**
     * 管理员：修改
     *
     * @param examine 页面提交的examine对象
     * @return 自定义的Result返回结果类对象
     */
    @PostMapping("/update/{examineId}")
    @ResponseBody
    public Result update(@Validated Examine examine, @PathVariable String examineId) {
        examine.setExamineId(examineId);
        examineService.update(examine);
        return new Result(200, "修改成功");
    }


}
