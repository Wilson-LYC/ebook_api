package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Token接口",description = "v1")
@RestController
@RequestMapping("/v1/token")
public class TokenController {
    @Autowired
    TokenService tokenService;

    @Operation(summary = "登录")
    @PostMapping("/pwd")
    public JSONObject login(
            @Parameter(description = "邮箱")
            @RequestParam String email,
            @Parameter(description = "密码")
            @RequestParam String password){
        return tokenService.loginByPwd(email, password);
    }

    @Operation(summary = "验证码登录+自动注册")
    @PostMapping("")
    public JSONObject loginByCaptcha(
            @Parameter(description = "邮箱")
            @RequestParam String email,
            @Parameter(description = "验证码")
            @RequestParam String captcha){
        return tokenService.loginByCaptcha(email, captcha);
    }

    @GetMapping("/{token}")
    public JSONObject verify(@PathVariable String token){
        return tokenService.verify(token);
    }

}
