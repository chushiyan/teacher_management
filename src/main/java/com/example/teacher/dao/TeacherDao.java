package com.example.teacher.dao;

import com.example.teacher.pojo.Admin;
import com.example.teacher.pojo.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author
 * @email
 * @description 教师dao
 */

public interface TeacherDao extends JpaRepository<Teacher, String>, JpaSpecificationExecutor<Teacher> {

    Teacher findTeacherByEmailEquals(String email);
}
