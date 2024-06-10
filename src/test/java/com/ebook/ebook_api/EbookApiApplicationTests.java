package com.ebook.ebook_api;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.ebook.ebook_api.mapper.UserMapper;
import com.ebook.ebook_api.service.AiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EbookApiApplicationTests {

    @Autowired
    AiService aiService;
    @Test
    void contextLoads() {
        try {
            System.out.println(aiService.getAnswer("4个子形容阿里巴巴"));
        } catch (NoApiKeyException e) {
            e.printStackTrace();
        } catch (InputRequiredException e) {
            e.printStackTrace();
        }
    }
}
