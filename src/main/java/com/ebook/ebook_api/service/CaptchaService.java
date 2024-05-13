package com.ebook.ebook_api.service;

import com.alibaba.fastjson2.JSONObject;

public interface CaptchaService {
    String create();
    void save(String email, String captcha);
    JSONObject send(String email);
}
