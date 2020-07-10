package com.example.teacher.service;

import cn.hutool.core.lang.Snowflake;
import com.example.teacher.dao.ExamineDao;
import com.example.teacher.dao.TeacherDao;
import com.example.teacher.exception.JsonResultRuntimeException;
import com.example.teacher.pojo.Application;
import com.example.teacher.pojo.Examine;
import com.example.teacher.pojo.Examine;
import com.example.teacher.pojo.Teacher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author
 * @email
 * @description
 */
@Service
@Transactional
public class ExamineService {


    @Autowired
    private ExamineDao examineDao;


    public Page<Examine> list(int page, int limit, String name) {
        Specification<Examine> specification = null;
        if (StringUtils.isNotEmpty(name)) {
            specification = new Specification<Examine>() {
                @Override
                public Predicate toPredicate(Root<Examine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> predicateList = new ArrayList<Predicate>();

                    // 两张表关联查询
                    Join<Examine, Teacher> join = root.join("teacher", JoinType.INNER);
                    predicateList.add(criteriaBuilder.like(join.get("name").as(String.class), "%" + name + "%"));

                    return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
                }
            };
        }
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by("createTime").descending());

        return examineDao.findAll(specification, pageRequest);


    }

    public Page<Examine> mine(int page, int limit, String teacherId) {
        Specification<Examine> specification = new Specification<Examine>() {
            @Override
            public Predicate toPredicate(Root<Examine> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<Predicate>();

                // 两张表关联查询
                Join<Examine, Teacher> join = root.join(root.getModel().getSingularAttribute("teacher", Teacher.class), JoinType.LEFT);
                predicateList.add(criteriaBuilder.equal(join.get("teacherId").as(String.class), teacherId));

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by("createTime").descending());

        return examineDao.findAll(specification, pageRequest);

    }


    // Hutool库工具类
    @Autowired
    private Snowflake snowflake;

    @Autowired
    private TeacherDao teacherDao;

    // 添加
    public void add(Examine examine, String teacherId) {
        Optional<Teacher> teacherOptional = teacherDao.findById(teacherId);
        if (!teacherOptional.isPresent()) {
            throw new JsonResultRuntimeException("未查询到指定教师");
        }
        examine.setTeacher(teacherOptional.get());
        examine.setExamineId(String.valueOf(snowflake.nextId()));
        examine.setCreateTime(new Timestamp(new Date().getTime()));
        examine.setStatus(1);
        examineDao.save(examine);
    }


    public void deleteById(int status, String examineId) {
        examineDao.updateStatus(status, examineId);
    }


    public void update(Examine examine) {
        Optional<Examine> examineOptional = examineDao.findById(examine.getExamineId());
        if (!examineOptional.isPresent()) {
            throw new JsonResultRuntimeException("未查询到指定考核信息");
        }

        Examine examineFromDB = examineOptional.get();

        examineFromDB.setExamineName(examine.getExamineName());

        examineFromDB.setExamineDetail(examine.getExamineDetail());

        examineDao.save(examineFromDB);
    }
}
