package com.example.teacher.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {

    private Integer status;// 状态码

    private String message;// 返回信息

    private Long total;// 数据条数

    private Object rows;// 返回数据

    public Result(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(Integer status, String message, Long total, Object rows) {
        this.status = status;
        this.message = message;
        this.total = total;
        this.rows = rows;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }
}
