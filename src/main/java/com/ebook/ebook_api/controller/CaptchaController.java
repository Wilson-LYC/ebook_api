package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.CaptchaService;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/captcha")
@Tag(name = "验证码接口")
public class CaptchaController {
    @Autowired
    CaptchaService captchaService;

    @Operation(summary = "发送验证码到邮箱")
    @PostMapping("")
    public JSONObject getCaptcha(
            @Parameter(description = "邮箱", required = true)
            @Param("email") String email) {
        return captchaService.send(email);
    }

    @Operation(summary = "验证验证码")
    @GetMapping("")
    public JSONObject verifyCaptcha(
            @Parameter(description = "邮箱")
            @RequestParam("email") String email,
            @Parameter(description = "验证码")
            @RequestParam("captcha") String captcha) {
        return captchaService.verify(email,captcha);
    }
}
