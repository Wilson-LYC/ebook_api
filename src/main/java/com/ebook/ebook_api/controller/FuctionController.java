package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.FunctionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/function")
@Tag(name = "函数接口")
public class FuctionController {
    @Autowired
    FunctionService functionService;

    @Operation(summary = "获取本周推荐函数")
    @GetMapping("/recommended")
    public JSONObject getRecommended() {
        return functionService.getRecommended();
    }
    @Operation(summary = "获取类别")
    @GetMapping("/category")
    public JSONObject getCategory(){
        return functionService.getCategory();
    }
    @Operation(summary = "根据类别id获取函数列表")
    @GetMapping("/category/{id}")
    public JSONObject getFunctionByCid(@Parameter(description = "函数id") @PathVariable int id) {
        return functionService.getFunctionByCid(id);
    }
    @Operation(summary = "根据函数id获取函数")
    @GetMapping("/{id}")
    public JSONObject getFunctionById(
            @Parameter(description = "token")
            @RequestHeader(name = "Authorization") String token,
            @Parameter(description = "函数id")
            @PathVariable int id) {
        return functionService.getFunctionById(id,token);
    }
    @Operation(summary = "点赞")
    @PostMapping("/like/{fid}")
    public JSONObject likeFunction(
            @Parameter(description = "token")
            @RequestHeader(name = "Authorization") String token,
            @Parameter(description = "函数id")
            @PathVariable int fid) {
        return functionService.likeFunction(fid,token);
    }

    @Operation(summary = "取消点赞")
    @DeleteMapping("/like/{fid}")
    public JSONObject disLikeFunction(
            @Parameter(description = "token")
            @RequestHeader(name = "Authorization") String token,
            @Parameter(description = "函数id")
            @PathVariable int fid) {
        return functionService.disLikeFunction(fid,token);
    }

}
