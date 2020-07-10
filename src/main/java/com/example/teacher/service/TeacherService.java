package com.example.teacher.service;

import com.example.teacher.dao.TeacherDao;
import com.example.teacher.exception.JsonResultRuntimeException;
import com.example.teacher.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author
 * @email
 * @description 管理员
 */
@Service
@Transactional
public class TeacherService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TeacherDao teacherDao;

    public Teacher login(Teacher teacher) {
        // 根据邮箱查询
        Teacher teacherFromDB = teacherDao.findTeacherByEmailEquals(teacher.getEmail());

        if (teacherFromDB != null && bCryptPasswordEncoder.matches(teacher.getPassword(), teacherFromDB.getPassword())) {
            return teacherFromDB;
        }
        return null;

    }

    public void changePassword(String password, String password2, Teacher teacher) {
        if (!bCryptPasswordEncoder.matches(password, teacher.getPassword())) {
            throw new JsonResultRuntimeException("原密码错误");
        }
        teacher.setPassword(bCryptPasswordEncoder.encode(password2));
        teacherDao.save(teacher);
    }


    // 首次登录修改密码
    public void resetPassword(String password, String password2, Teacher teacher) {
        if (!bCryptPasswordEncoder.matches(password, teacher.getPassword())) {
            throw new JsonResultRuntimeException("原密码错误");
        }
        teacher.setPassword(bCryptPasswordEncoder.encode(password2));
        teacher.setStatus(1);
        teacherDao.save(teacher);
    }

    public List<Teacher> findAll() {

        return teacherDao.findAll();
    }


    public void update(Teacher teacherFromSession) {
        teacherDao.save(teacherFromSession);
    }



}
