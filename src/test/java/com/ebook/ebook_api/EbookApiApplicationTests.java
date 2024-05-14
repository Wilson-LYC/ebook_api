package com.ebook.ebook_api;

import com.ebook.ebook_api.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EbookApiApplicationTests {

    @Autowired
    AccountMapper accountMapper;
    @Test
    void contextLoads() {
        System.out.println(accountMapper.selectList(null));
    }
}
