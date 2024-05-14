package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/account")
@Tag(name = "帐号接口")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Operation(summary = "注册")
    @PostMapping("")
    public JSONObject register(
            @Parameter(description = "邮箱")
            @RequestParam String email,
            @Parameter(description = "密码")
            @RequestParam String password,
            @Parameter(description = "验证码")
            @RequestParam String captcha,
            @Parameter(description = "验证码key")
            @RequestParam String key){
        return accountService.register(email, password, captcha, key);
    }
}
