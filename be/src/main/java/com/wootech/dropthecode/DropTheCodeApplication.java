package com.wootech.dropthecode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
@SpringBootApplication
public class DropTheCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DropTheCodeApplication.class, args);
    }
}
