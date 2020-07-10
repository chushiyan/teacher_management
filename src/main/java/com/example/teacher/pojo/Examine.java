package com.example.teacher.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author
 * @email
 * @description 考核表实体类
 */

@Data
@Entity
@EqualsAndHashCode(exclude = {"teacher"}) // 忽略属性，否则出现死循环
@ToString(exclude = {"teacher"})   // 忽略属性，否则出现死循环
public class Examine implements Serializable {

    @Id
    @Column(name = "examine_id")
    private String examineId;

    // 考核对应的教师
    //    private String teacherId;
// 配置教师表和考核表的一对多关系
    @ManyToOne(targetEntity = Teacher.class)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    @JsonIgnoreProperties(value = {"applicationSet", "examineSet"})
    private Teacher teacher;

    @Column(name = "examine_name")
    private String examineName;   // 考核名称

    @Column(name = "examine_detail")
    private String examineDetail;  // 考核详情

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime; // 考核创建时间

    @Column(name = "startTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp startTime;  // 考核开始日期

    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp endTime;   // 考核结束日期

    private Integer status;  // 状态。0：已删除。1：未开始。2：进行中。3：已完成

}