package com.example.teacher.dao;

import com.example.teacher.pojo.Application;
import com.example.teacher.pojo.Promote;
import com.example.teacher.pojo.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author
 * @email
 * @description 申请表dao
 */

public interface ApplicationDao extends JpaRepository<Application, String>, JpaSpecificationExecutor<Application> {

    @Query(value = "update Application set status = ?1 where applicationId = ?2")
    @Modifying
    void updateStatus(int status, String applicationId);


}
