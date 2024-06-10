package com.ebook.ebook_api.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ebook.ebook_api.dto.ResponseDto;
import com.ebook.ebook_api.mapper.CommentMapper;
import com.ebook.ebook_api.pojo.Comment;
import com.ebook.ebook_api.service.CommentService;
import com.ebook.ebook_api.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public ResponseDto getCommentListByFid(Integer fid) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fid", fid);
        List<Comment> commentList;
        try {
            commentList = commentMapper.selectList(queryWrapper);
        } catch (Exception e) {
            log.error("获取评论列表失败（mysql）", e);
            return new ResponseDto(500, "系统错误");
        }
        JSONObject data = new JSONObject();
        data.put("comments", commentList);
        return new ResponseDto(200, "成功", data);
    }

    @Override
    public ResponseDto sendComment(String token, Integer fid, String content) {
        JSONObject tokenJson = TokenUtil.decode(token);
        if (tokenJson.getInteger("code")!=200) {
            return new ResponseDto(400, "未登录");
        }
        Comment comment = new Comment();
        comment.setFid(fid);
        comment.setUid(tokenJson.getInteger("uid"));
        comment.setContent(content);
        try {
            commentMapper.insert(comment);
        } catch (Exception e) {
            log.error("发送评论失败（mysql）", e);
            return new ResponseDto(500, "系统错误");
        }
        return new ResponseDto(200, "成功");
    }
}
