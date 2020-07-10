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
 * @description 申请表实体类
 */
@Data
@Entity
@EqualsAndHashCode(exclude = {"teacher", "promote"}) // 忽略属性，否则出现死循环
@ToString(exclude = {"teacher", "promote"})   // 忽略属性，否则出现死循环
public class Application implements Serializable {

    @Id
    @Column(name = "application_id")
    private String applicationId;

    //    private String teacherId;
    // 配置教师表和申请表的一对多关系
    @ManyToOne(targetEntity = Teacher.class)
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
    @JsonIgnoreProperties(value = {"applicationSet", "examineSet"})
    private Teacher teacher;


    //    private String promoteId;
    // 配置晋升表和申请表的一对多关系
    @ManyToOne(targetEntity = Promote.class)
    @JoinColumn(name = "promote_id", referencedColumnName = "promote_id")
    @JsonIgnoreProperties(value = {"applicationSet"})
    private Promote promote;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;   // 申请创建时间

    private Integer status;    // '状态。-1：待审核。0：已删除。1：已通过。2：未通过',

}