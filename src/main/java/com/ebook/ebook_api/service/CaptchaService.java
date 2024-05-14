package com.ebook.ebook_api.service;

import com.alibaba.fastjson2.JSONObject;

public interface CaptchaService {
    JSONObject send(String email);

    JSONObject verify(String captcha, String key);
}
