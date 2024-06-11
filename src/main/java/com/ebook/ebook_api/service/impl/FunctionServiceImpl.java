package com.ebook.ebook_api.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ebook.ebook_api.dto.ResponseDto;
import com.ebook.ebook_api.mapper.CategoryMapper;
import com.ebook.ebook_api.mapper.CommentMapper;
import com.ebook.ebook_api.mapper.FunctionLikeMapper;
import com.ebook.ebook_api.mapper.FunctionMapper;
import com.ebook.ebook_api.pojo.Category;
import com.ebook.ebook_api.pojo.Comment;
import com.ebook.ebook_api.pojo.Function;
import com.ebook.ebook_api.pojo.FunctionLike;
import com.ebook.ebook_api.service.FunctionService;
import com.ebook.ebook_api.service.TokenService;
import com.ebook.ebook_api.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    FunctionMapper functionMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    FunctionLikeMapper functionLikeMapper;
    @Autowired
    TokenService tokenService;

    @Override
    public ResponseDto getRecommended() {
        QueryWrapper<Function> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recommend", 1);
        List<Function> functionList;
        try{
            functionList = functionMapper.selectList(queryWrapper);
        }catch (Exception e) {
            log.error("获取推荐函数失败（mysql）",e);
            return new ResponseDto(500, "系统错误");
        }
        JSONArray functionArray = new JSONArray();
        for (Function function : functionList) {
            functionArray.add(function.toJson());
        }
        JSONObject data = new JSONObject();
        data.put("functions", functionArray);
        return new ResponseDto(200, "success", data);
    }

    @Override
    public ResponseDto getCategory() {
        List<Category> categoryList;
        try{
            categoryList = categoryMapper.selectList(null);
        }catch (Exception e) {
            log.error("获取类别失败（mysql）",e);
            return new ResponseDto(500, "系统错误");
        }
        JSONArray array=JSONArray.from(categoryList);
        JSONObject data = new JSONObject();
        data.put("categories", array);
        return new ResponseDto(200, "success", data);
    }

    @Override
    public ResponseDto getFunctionByCid(int id) {
        QueryWrapper<Function> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid", id);
        List<Function> functionList;
        try {
            functionList = functionMapper.selectList(queryWrapper);
        }catch (Exception e) {
            log.error("获取函数列表失败（mysql）",e);
            return new ResponseDto(500, "系统错误");
        }
        JSONArray array=JSONArray.from(functionList);
        JSONObject data = new JSONObject();
        data.put("functions", array);
        return new ResponseDto(200, "成功", data);
    }

    @Override
    public ResponseDto getFunctionById(int id, String token) {
        JSONObject tokenJson = TokenUtil.decode(token);
        if (tokenJson.getInteger("code")!=200) {
            return new ResponseDto(400, "未登录");
        }
        // 获取函数
        Function function;
        try {
            function = functionMapper.selectById(id);
        }catch (Exception e) {
            log.error("获取函数失败（mysql）",e);
            return new ResponseDto(500, "系统错误");
        }
        JSONObject data = new JSONObject();
        data.put("function", function.toJson());
        // 获取评论数量
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fid", id);
        Long commentCount;
        try {
            commentCount = commentMapper.selectCount(queryWrapper);
        }catch (Exception e) {
            log.error("获取评论数量失败（mysql）",e);
            return new ResponseDto(500, "系统错误");
        }
        // 获取点赞数量
        QueryWrapper<FunctionLike> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("fid", id);
        Long likeCount;
        try {
            likeCount = functionLikeMapper.selectCount(queryWrapper1);
        }catch (Exception e) {
            log.error("获取点赞数量失败（mysql）",e);
            return new ResponseDto(500, "系统错误");
        }
        data.put("commentCount", commentCount);
        data.put("likeCount", likeCount);
        //检查用户是否点赞
        QueryWrapper<FunctionLike> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("fid", id);
        queryWrapper2.eq("uid", tokenJson.getInteger("uid"));
        Long like;
        try {
            like = functionLikeMapper.selectCount(queryWrapper2);
        }catch (Exception e) {
            log.error("检查用户是否点赞失败（mysql）",e);
            return new ResponseDto(500, "系统错误");
        }
        data.put("like", like>0);
        return new ResponseDto(200, "成功", data);
    }

    @Override
    public ResponseDto likeFunction(int fid, String token) {
        JSONObject tokenJson = TokenUtil.decode(token);
        if (tokenJson.getInteger("code")!=200) {
            return new ResponseDto(400, "未登录");
        }
        FunctionLike functionLike = new FunctionLike();
        functionLike.setFid(fid);
        functionLike.setUid(tokenJson.getInteger("uid"));
        try {
            functionLikeMapper.insert(functionLike);
        }catch (Exception e) {
            log.error("点赞失败（mysql）",e);
            return new ResponseDto(500, "系统错误");
        }
        return new ResponseDto(200, "成功");
    }

    @Override
    public ResponseDto disLikeFunction(int fid, String token) {
        JSONObject tokenJson = TokenUtil.decode(token);
        if (tokenJson.getInteger("code")!=200) {
            return new ResponseDto(400, "未登录");
        }
        QueryWrapper<FunctionLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fid", fid);
        queryWrapper.eq("uid", tokenJson.getInteger("uid"));
        try {
            functionLikeMapper.delete(queryWrapper);
        }catch (Exception e) {
            log.error("取消点赞失败（mysql）",e);
            return new ResponseDto(500, "系统错误");
        }
        return new ResponseDto(200, "成功");
    }
}
