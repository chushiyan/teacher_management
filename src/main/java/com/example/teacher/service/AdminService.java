package com.example.teacher.service;

import com.example.teacher.dao.AdminDao;
import com.example.teacher.exception.JsonResultRuntimeException;
import com.example.teacher.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 * @email
 * @description 管理员
 */
@Service
@Transactional
public class AdminService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AdminDao adminDao;

    public Admin login(Admin admin) {
        // 根据邮箱查询
        Admin adminFromDB = adminDao.findAdminByEmailEquals(admin.getEmail());

        if (adminFromDB != null && bCryptPasswordEncoder.matches(admin.getPassword(), adminFromDB.getPassword())) {
            return adminFromDB;
        }
        return null;

    }

    public void changePassword(String password, String password2, Admin admin) {

        if(!bCryptPasswordEncoder.matches(password,admin.getPassword())){
            throw new JsonResultRuntimeException("原密码错误");
        }
        admin.setPassword(bCryptPasswordEncoder.encode(password2));

        adminDao.save(admin);

    }
}
