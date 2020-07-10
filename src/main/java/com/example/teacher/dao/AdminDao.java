package com.example.teacher.dao;

import com.example.teacher.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author
 * @email
 * @description 管理员dao
 */

public interface AdminDao extends JpaRepository<Admin, String>, JpaSpecificationExecutor<Admin> {


    Admin findAdminByEmailEquals(String email);


}
