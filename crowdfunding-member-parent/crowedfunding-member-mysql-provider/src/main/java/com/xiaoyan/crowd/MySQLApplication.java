package com.xiaoyan.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaoyan.crowd.mapper")
public class MySQLApplication {


    public static void main(String[] args) {
        SpringApplication.run(MySQLApplication.class,args);
    }

}
