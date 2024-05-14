package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.CaptchaService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/captcha")
public class CaptchaController {
    @Autowired
    CaptchaService captchaService;

    /**
     * 发送验证码
     * @param email 邮箱
     * @return 发送结果
     */
    @PostMapping("")
    public JSONObject getCaptcha(@Param("email") String email) {
        return captchaService.send(email);
    }

    /**
     * 验证验证码
     */
    @GetMapping("/{key}")
    public JSONObject verifyCaptcha(@Param("captcha") String captcha, @PathVariable("key") String key) {
        return captchaService.verify(captcha, key);
    }
}
