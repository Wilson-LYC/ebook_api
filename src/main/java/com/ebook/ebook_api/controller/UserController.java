package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Tag(name = "帐号接口",description = "v1")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "注册")
    @PostMapping("")
    public JSONObject register(
            @Parameter(description = "邮箱")
            @RequestParam String email,
            @Parameter(description = "密码")
            @RequestParam String password,
            @Parameter(description = "验证码")
            @RequestParam String captcha){
        return userService.register(email, password, captcha);
    }
    @Operation(summary = "通过token获取用户信息")
    @GetMapping("/token")
    public JSONObject getUserByToken(@RequestHeader(name = "Authorization") String token){
        return userService.getUserByToken(token);
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/info")
    public JSONObject updateUserInfo(
            @Parameter(description = "token")
            @RequestHeader(name = "Authorization") String token,
            @Parameter(description = "用户id")
            @RequestParam int id,
            @Parameter(description = "昵称")
            @RequestParam String name){
        return userService.updateUserInfo(token, id, name);
    }
    @Operation(summary = "换绑邮箱")
    @PutMapping("/email")
    public JSONObject updateEmail(
            @Parameter(description = "token")
            @RequestHeader(name = "Authorization") String token,
            @Parameter(description = "用户id")
            @RequestParam int id,
            @Parameter(description = "邮箱")
            @RequestParam String email,
            @Parameter(description = "验证码")
            @RequestParam String captcha){
        return userService.updateEmail(token, id, email, captcha);
    }

    @Operation(summary = "重置密码")
    @PutMapping("/password")
    public JSONObject resetPassword(
            @Parameter(description = "邮箱")
            @RequestParam String email,
            @Parameter(description = "新密码")
            @RequestParam String password,
            @Parameter(description = "验证码")
            @RequestParam String captcha){
        return userService.resetPassword(email,password, captcha);
    }
}
