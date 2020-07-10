package com.example.teacher.exception;

/**
 * @author
 * @email
 * @description 自定义异常类。但凡反生异常、且返回json数据给前端，使用此异常
 */

public class JsonResultRuntimeException extends RuntimeException {
    // 异常信息
    private String message;

    public JsonResultRuntimeException() {
        super();
    }

    public JsonResultRuntimeException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

