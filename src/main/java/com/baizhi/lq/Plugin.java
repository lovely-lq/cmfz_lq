package com.baizhi.lq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.lq.dao")
public class Plugin {
    public static void main(String[] args) {
        SpringApplication.run(Plugin.class, args);
    }
}
