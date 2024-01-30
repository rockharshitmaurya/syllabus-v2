package com.syllabus.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class SyllabusMsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyllabusMsaApplication.class, args);
    }

}
