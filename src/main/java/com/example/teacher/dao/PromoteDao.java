package com.example.teacher.dao;

import com.example.teacher.pojo.Promote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author
 * @email
 * @description 晋升表dao
 */

public interface PromoteDao extends JpaRepository<Promote, String>, JpaSpecificationExecutor<Promote> {

    Page<Promote> findAllByNameLike(String name, Pageable pageable);

    @Query(value = "update Promote set status = ?1 where promoteId = ?2")
    @Modifying
    void updateStatus(int status, String promoteId);
}
