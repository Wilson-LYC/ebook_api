package com.ebook.ebook_api.service;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.dto.ResponseDto;

public interface FunctionService {
    ResponseDto getRecommended();

    ResponseDto getCategory();

    ResponseDto getFunctionByCid(int id);

    ResponseDto getFunctionById(int id);
}
