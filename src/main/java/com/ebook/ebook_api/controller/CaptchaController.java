package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.CaptchaService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/v1/captcha")
public class CaptchaController {
    @Autowired
    CaptchaService captchaService;
    @PostMapping("")
    public JSONObject getCaptcha(@Param("email") String email) {
        return captchaService.send(email);
    }
}
