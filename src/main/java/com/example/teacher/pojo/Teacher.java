package com.example.teacher.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author
 * @email
 * @description 教师表实体类
 */

@Data
@Entity
@EqualsAndHashCode(exclude = {"applicationSet", "examineSet"}) // 忽略属性，否则出现死循环
@ToString(exclude = {"applicationSet", "examineSet"})   // 忽略属性，否则出现死循环
public class Teacher implements Serializable {

    @Id
    @Column(name = "teacher_id")
    private String teacherId;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private String phone;

    private Integer age;

    private Integer gender;   //  '性别。0：女。1：男',

    private String birthplace; // 籍贯

    @Column(name = "id_number")
    private String idNumber; // 身份证号码

    @Column(name = "graduate_school")
    private String graduateSchool; // 毕业院校

    private String education;  // 学历

    @Column(name = "job_grade")
    private String jobGrade;  // 职称职级。助教、讲师、副教授、教授

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date hiredate;   // 入职时间

    private String department;  // 所属部门

    private Integer status;  // '状态。-1：未激活（首次登陆需要修改密码，修改后状态修改为1）。0：已离职。1：正常中',


    // 配置教师表和申请表的一对多关系
    @OneToMany(mappedBy = "teacher")
    @OrderBy("createTime DESC")  // 根据createTime降序
    @JsonIgnoreProperties(value = {"teacher", "promote"})
    private Set<Application> applicationSet = new HashSet<Application>(0);


    // 配置教师表和考核表的一对多关系
    @OneToMany(mappedBy = "teacher")
    @OrderBy("createTime DESC")
    @JsonIgnoreProperties(value = {"teacher"})
    private Set<Examine> examineSet = new HashSet<Examine>(0);


}