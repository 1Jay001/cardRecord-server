package com.thaddeus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.thaddeus.server.mapper")
public class CardRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardRecordApplication.class, args);
    }

}
