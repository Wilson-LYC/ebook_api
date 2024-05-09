package com.ebook.ebook_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class EbookApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbookApiApplication.class, args);
    }

}
