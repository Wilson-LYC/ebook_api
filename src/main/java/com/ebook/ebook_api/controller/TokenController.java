package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/token")
public class TokenController {
    @Autowired
    TokenService tokenService;
    @PostMapping("")
    public JSONObject login(@RequestParam String email, @RequestParam String password){
        return tokenService.login(email, password);
    }

    @GetMapping("/{token}")
    public JSONObject verify(@PathVariable String token){
        return tokenService.verify(token);
    }

}
