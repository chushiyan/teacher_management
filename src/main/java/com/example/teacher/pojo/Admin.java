package com.example.teacher.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author
 * @email
 * @description 管理员
 */

@Data
@Entity
public class Admin implements Serializable {

    @Id
    @Column(name = "admin_id")
    private String adminId;


    @Email(message = "邮箱格式不正确")
    @NotNull(message = "邮箱不能为空")
    private String email;

    @NotNull(message = "密码不能为空")
    @JsonIgnore
    private String password;

}