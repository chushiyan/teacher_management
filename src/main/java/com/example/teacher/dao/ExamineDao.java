package com.example.teacher.dao;

import com.example.teacher.pojo.Examine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author
 * @email
 * @description 考核表 dao
 */

public interface ExamineDao extends JpaRepository<Examine, String>, JpaSpecificationExecutor<Examine> {

    @Query(value = "update Examine set status = ?1 where examineId = ?2")
    @Modifying
    void updateStatus(int status, String examineId);
}
