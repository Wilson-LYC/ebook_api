package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.AiService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/ai")
public class AiController {

    @Autowired
    AiService aiService;
    @PostMapping("")
    public JSONObject getAnswer(
            @Parameter(description = "问题")
            @RequestParam String question) {
        return aiService.getAnswer(question);
    }
}
