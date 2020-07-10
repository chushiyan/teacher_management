package com.example.teacher.controller;

import com.example.teacher.pojo.Promote;
import com.example.teacher.service.PromoteService;
import com.example.teacher.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @email
 * @description
 */
@Controller
@CrossOrigin
@RequestMapping("promote")
public class PromoteController {

    @Autowired
    private PromoteService promoteService;


    /**
     * 管理员：单条件分页查询晋升表
     *
     * @param page  前端提交的page当前页码
     * @param limit 前端提交的每页显示条数
     * @param query 前端提交的查询参数
     * @return 自定义的Result返回结果类对象
     */
    @RequestMapping("/list")
    @ResponseBody
    public Result list(@RequestParam int page,
                       @RequestParam int limit,
                       @RequestParam(required = false, defaultValue = "") String query) {

        Page<Promote> promotePage = promoteService.list(page, limit, query);

        return new Result(200, "查询成功",
                promotePage.getTotalElements(),
                promotePage.getContent());
    }


    /**
     * 管理员：增加
     *
     * @param promote 页面提交的promote对象
     * @return 自定义的Result返回结果类对象
     */
    @PostMapping
    @ResponseBody
    public Result add(@Validated Promote promote) {
        promoteService.add(promote);
        return new Result(200, "增加成功");
    }


    /**
     * 管理员：根据 id 逻辑删除
     *
     * @param promoteId 页面提交的id
     * @return 自定义的Result返回结果类对象
     */
    @DeleteMapping("/{promoteId}")
    @ResponseBody
    public Result deleteById(@PathVariable String promoteId) {
        promoteService.deleteById(0, promoteId);
        return new Result(200, "删除成功");
    }

    /**
     * 管理员：修改
     *
     * @param promote 页面提交的promote对象
     * @return 自定义的Result返回结果类对象
     */
    @PostMapping("/update/{promoteId}")
    @ResponseBody
    public Result update(@Validated Promote promote, @PathVariable String promoteId) {
        promote.setPromoteId(promoteId);
        promoteService.update(promote);
        return new Result(200, "修改成功");
    }


}
