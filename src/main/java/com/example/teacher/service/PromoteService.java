package com.example.teacher.service;

import cn.hutool.core.lang.Snowflake;
import com.example.teacher.dao.PromoteDao;
import com.example.teacher.exception.JsonResultRuntimeException;
import com.example.teacher.pojo.Promote;
import com.example.teacher.pojo.Teacher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class PromoteService {


    @Autowired
    private PromoteDao promoteDao;


    public Page<Promote> list(int page, int limit, String query) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createTime").descending());

        return promoteDao.findAllByNameLike("%" + query + "%", pageable);

    }

    // Hutool库工具类
    @Autowired
    private Snowflake snowflake;

    // 添加
    public void add(Promote promote) {
        promote.setPromoteId(String.valueOf(snowflake.nextId()));
        promote.setCreateTime(new Timestamp(new Date().getTime()));
        promote.setStatus(1);
        promoteDao.save(promote);
    }


    public void deleteById(int status, String promoteId) {
        promoteDao.updateStatus(status, promoteId);
    }

    public void update(Promote promote) {

        Optional<Promote> promoteOptional = promoteDao.findById(promote.getPromoteId());

        if (!promoteOptional.isPresent()) {
            throw new JsonResultRuntimeException("未查询到指定晋升数据");
        }
        promoteDao.save(promote);
    }

    public List<Promote> findAll() {

        return promoteDao.findAll();
    }
}
