package com.example.teacher.service;

import cn.hutool.core.lang.Snowflake;
import com.example.teacher.dao.ApplicationDao;
import com.example.teacher.dao.PromoteDao;
import com.example.teacher.exception.JsonResultRuntimeException;
import com.example.teacher.pojo.Application;
import com.example.teacher.pojo.Promote;
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
public class ApplicationService {


    @Autowired
    private ApplicationDao applicationDao;


    public Page<Application> list(int page, int limit, String name) {

        Specification<Application> specification = null;

        if (StringUtils.isNotEmpty(name)) {
            specification = new Specification<Application>() {
                @Override
                public Predicate toPredicate(Root<Application> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                    List<Predicate> predicateList = new ArrayList<Predicate>();

                    // 两张表关联查询
                    Join<Application, Teacher> join = root.join(root.getModel().getSingularAttribute("teacher", Teacher.class), JoinType.INNER);
                    predicateList.add(criteriaBuilder.like(join.get("name").as(String.class), "%" + name + "%"));
                    return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
                }
            };
        }
        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by("createTime").descending());

        return applicationDao.findAll(specification, pageRequest);


    }

    public Page<Application> mine(int page, int limit, String teacherId) {
        Specification<Application> specification = new Specification<Application>() {
            @Override
            public Predicate toPredicate(Root<Application> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<Predicate>();

                // 两张表关联查询
                Join<Application, Teacher> join = root.join(root.getModel().getSingularAttribute("teacher", Teacher.class), JoinType.LEFT);
                predicateList.add(criteriaBuilder.equal(join.get("teacherId").as(String.class), teacherId));

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        PageRequest pageRequest = PageRequest.of(page - 1, limit, Sort.by("createTime").descending());

        return applicationDao.findAll(specification, pageRequest);

    }


    // Hutool库工具类
    @Autowired
    private Snowflake snowflake;


    @Autowired
    private PromoteDao promoteDao;

    // 添加
    public void add(String promoteId, Teacher teacher) {

        Optional<Promote> promoteOptional = promoteDao.findById(promoteId);

        if (!promoteOptional.isPresent()) {
            throw new JsonResultRuntimeException("未查询到晋升信息");
        }

        Application application = new Application();

        application.setApplicationId(String.valueOf(snowflake.nextId()));

        application.setTeacher(teacher);

        application.setPromote(promoteOptional.get());

        application.setCreateTime(new Timestamp(new Date().getTime()));

        application.setStatus(-1); // 状态值为-1，待审核

        applicationDao.save(application);

    }


    public void deleteById(int status, String applicationId) {
        applicationDao.updateStatus(status, applicationId);
    }

}
