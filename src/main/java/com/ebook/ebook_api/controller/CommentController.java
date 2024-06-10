package com.ebook.ebook_api.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.CommentService;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comment")
public class CommentController {

    @Autowired
    CommentService commentService;
    @Operation(summary = "获取评论列表")
    @GetMapping("")
    public JSONObject getCommentListByFid(
            @Parameter(description = "函数id")
            @RequestParam("fid") Integer fid) {
        return commentService.getCommentListByFid(fid);
    }
    @Operation(summary = "发送评论")
    @PostMapping("")
    public JSONObject sendComment(
            @Parameter(description = "token")
            @RequestHeader(name = "Authorization") String token,
            @Parameter(description = "函数id")
            @RequestParam("fid") Integer fid,
            @Parameter(description = "评论内容")
            @RequestParam("content") String content) {
        return commentService.sendComment(token,fid, content);
    }
}
