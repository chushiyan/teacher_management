package com.example.teacher.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * @author
 * @email
 * @description 晋升表实体类
 */

@Data
@Entity
@EqualsAndHashCode(exclude = {"applicationSet"}) // 忽略属性，否则出现死循环
@ToString(exclude = {"applicationSet"})   // 忽略属性，否则出现死循环
public class Promote implements Serializable {

    @Id
    @Column(name = "promote_id")
    private String promoteId;


    @NotNull(message = "名称不能为空")
    @Size(min = 2, max = 20, message = "名称长度必须在2-20之间")
    private String name; // 职称。助教、讲师、副教授、教授',


    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime; // 创建时间


    @NotNull(message = "晋升条件不能为空")
    @Size(min = 2, max = 5000, message = "晋升条件长度必须在2-5000之间")
    private String qualification;  // 晋升条件


    @Range(min = 0, max = 1, message = "晋升表属性值非法")
    private Integer status;  // 状态。0：已删除。1：正常中


    // 配置晋升表和申请表的一对多关系
    @OneToMany(mappedBy = "promote")
    @OrderBy("createTime DESC")  // 根据createTime降序
//    @JsonIgnoreProperties(value = {"teacher", "promote"})
    @JsonIgnore
    private Set<Application> applicationSet = new HashSet<Application>(0);


}