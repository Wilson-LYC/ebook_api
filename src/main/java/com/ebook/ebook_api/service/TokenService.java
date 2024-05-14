package com.ebook.ebook_api.service;

import com.alibaba.fastjson2.JSONObject;

public interface TokenService {
    JSONObject login(String email, String password);

    JSONObject verify(String token);
}
