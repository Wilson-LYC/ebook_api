package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.FunctionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/function")
@Tag(name = "函数接口")
public class FuctionController {
    @Autowired
    FunctionService functionService;
    @Operation(summary = "获取函数目录")
    @GetMapping("/catalogue")
    public JSONObject getCatalogue() {
        return functionService.getCatalogue();
    }
}
