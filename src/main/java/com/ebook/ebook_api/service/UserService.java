package com.ebook.ebook_api.service;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.dto.ResponseDto;

public interface UserService {
    ResponseDto register(String email, String password, String captcha);
    ResponseDto forgetPassword(String email, String password, String captcha);
    ResponseDto getUserByToken(String token);
    ResponseDto updateUserInfo(String token, int id, String name);
}
