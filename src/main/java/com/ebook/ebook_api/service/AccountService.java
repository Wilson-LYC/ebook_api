package com.ebook.ebook_api.service;

import com.alibaba.fastjson2.JSONObject;

public interface AccountService {
    JSONObject register(String email, String password, String captcha, String key);
}
