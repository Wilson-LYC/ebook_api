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
@RequestMapping("/v2/captcha")
@Tag(name = "验证码接口")
public class CaptchaV2Controller {
    @Autowired
    CaptchaService captchaService;

    /**
     * 发送验证码到邮箱 v2
     * @param email 邮箱
     * @return 发送结果
     */
    @Operation(summary = "发送验证码到邮箱")
    @PostMapping("")
    public JSONObject getCaptcha(
            @Parameter(description = "邮箱", required = true)
            @Param("email") String email) {
        return captchaService.send_v2(email);
    }

    /**
     * 验证验证码 v2
     * @param captcha 验证码
     * @param email 邮箱
     * @return 验证结果
     */
    @Operation(summary = "验证验证码")
    @GetMapping("")
    public JSONObject verifyCaptcha(
            @Parameter(description = "邮箱")
            @RequestParam("email") String email,
            @Parameter(description = "验证码")
            @RequestParam("captcha") String captcha) {
        return captchaService.verify_v2(email,captcha);
    }
}
