package com.example.teacher;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TeacherManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeacherManagementApplication.class, args);
    }

    // 注入spring security框架的加密工具类（用于加密用户密码）
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 注入hutool工具库的雪花算法生成ID的类（用于生成id）
    @Bean
    public Snowflake snowflake() {
        return IdUtil.createSnowflake(1, 1);
    }

}
