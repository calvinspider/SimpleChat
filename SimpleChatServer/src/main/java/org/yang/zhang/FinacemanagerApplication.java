package org.yang.zhang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("org.yang.zhang.mapper")
public class FinacemanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinacemanagerApplication.class, args);
    }
}
