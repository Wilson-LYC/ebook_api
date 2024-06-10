package com.ebook.ebook_api.service;

import com.alibaba.fastjson2.JSONObject;

public interface TokenService {
    JSONObject loginByPwd(String email, String password);
    JSONObject loginByCaptcha(String email, String captcha);
}
