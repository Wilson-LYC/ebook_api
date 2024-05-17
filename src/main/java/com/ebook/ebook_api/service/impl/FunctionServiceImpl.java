package com.ebook.ebook_api.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ebook.ebook_api.dto.ResponseDto;
import com.ebook.ebook_api.mapper.CategoryMapper;
import com.ebook.ebook_api.mapper.FunctionMapper;
import com.ebook.ebook_api.pojo.Category;
import com.ebook.ebook_api.pojo.Function;
import com.ebook.ebook_api.service.FunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunctionServiceImpl implements FunctionService {
    @Autowired
    FunctionMapper functionMapper;
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 获取函数目录
     * @return 函数目录
     */
    @Override
    public ResponseDto getCatalogue() {
        //获取所有函数类别
        List<Category> categoryList = categoryMapper.selectList(null);
        JSONArray functionArray = new JSONArray();
        for (Category category : categoryList) {
            JSONObject categoryJson = category.toJson();
            QueryWrapper<Function> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("cid", category.getId());
            queryWrapper.select("id", "name","intro");
            List<Function> functionList=functionMapper.selectList(queryWrapper);
            categoryJson.put("children", functionList);
            functionArray.add(categoryJson);
        }
        JSONObject data=new JSONObject();
        data.put("function",functionArray);
        return new ResponseDto(200, "成功", data);
    }

    /**
     * 获取函数详情
     * @param id 函数id
     * @return 函数详情
     */
    @Override
    public ResponseDto getFunctionById(String id) {
        Function function = functionMapper.selectById(id);
        if (function == null) {
            return new ResponseDto(404, "函数不存在");
        }
        JSONObject data = new JSONObject();
        data.put("function", function.toJson());
        return new ResponseDto(200, "成功", data);
    }
}
