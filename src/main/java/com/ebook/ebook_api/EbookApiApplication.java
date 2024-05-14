package com.ebook.ebook_api;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = {"com.ebook.ebook_api.mapper"})
public class EbookApiApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(EbookApiApplication.class, args);
    }
    @Override
    public void run(String... args) {
        //端口号默认8080
        log.info("Tomcat在端口 " + 8080 + " (http)上启动：");

        String hostname = "localhost";
        String http = "http://";

        log.info("Api文档 浏览器访问地址如下：");
        log.info(http + hostname + ":" + 8080 + "/swagger-ui/index.html");

        log.info("ApiFox的 URL方式 导入路径为：");
        log.info(http + hostname + ":" + 8080 + "/v3/api-docs");
    }
}
