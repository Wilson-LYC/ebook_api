package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/test")
@Tag(name = "测试接口",description = "v1")
public class TestController {
    @Operation(summary = "注册")
    @GetMapping("")
    public JSONObject test(){
        return new ResponseDto(200,"连接成功");
    }
}
