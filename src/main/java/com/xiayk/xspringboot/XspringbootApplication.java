package com.xiayk.xspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.xiayk.xspringboot.dao")
public class XspringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(XspringbootApplication.class, args);
    }

}

