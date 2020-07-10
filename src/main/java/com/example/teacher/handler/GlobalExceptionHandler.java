package com.example.teacher.handler;

import com.example.teacher.exception.JsonResultRuntimeException;
import com.example.teacher.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author
 * @email
 * @description
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有校验失败的异常（MethodArgumentNotValidException异常）
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    // 设置响应状态码为400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result handleBindGetException(MethodArgumentNotValidException ex) {
        // 获取所有异常
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        String msg = StringUtils.join(errors, "|");

        return new Result(400, msg);
    }


    /**
     * 处理所有参数校验时抛出的异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Result handleBindException(ValidationException ex) {

        Map<String, Object> body = new LinkedHashMap<String, Object>();
        body.put("timestamp", new Date());
        // 获取所有异常
        List<String> errors = new LinkedList<String>();
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                errors.add(item.getMessage());
            }
        }
        body.put("errors", errors);
        return new Result(400, StringUtils.join(errors.toArray(), "|"));
    }


    @ExceptionHandler(JsonResultRuntimeException.class) // 处理自定义异常
    @ResponseBody
    public Result managementException(HttpServletRequest request, Exception e, Model model) { // 出现异常之后会跳转到此方法
        return new Result(400, e.getMessage());
    }


//    @ExceptionHandler(RuntimeException.class) // 所有的异常都是Exception子类
    public String error(HttpServletRequest request, Exception e, Model model) { // 出现异常之后会跳转到此方法

//        model.addAttribute("exception", e); // 将异常对象传递过去
//        model.addAttribute("url", request.getRequestURL()); // 获得请求的路径
        model.addAttribute("msg", e.getMessage());
        return "/fail";// 跳转路径
    }


    //    @ExceptionHandler(Exception.class) // 所有的异常都是Exception子类
    public String defaultErrorHandler(HttpServletRequest request, Exception e, Model model) { // 出现异常之后会跳转到此方法

//        model.addAttribute("exception", e); // 将异常对象传递过去
//        model.addAttribute("url", request.getRequestURL()); // 获得请求的路径
        model.addAttribute("msg", e.getMessage());
        return "/fail";// 跳转路径
    }
}