package com.ebook.ebook_api.service;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.fastjson2.JSONObject;

public interface AiService {
    JSONObject getAnswer(String question);
}
