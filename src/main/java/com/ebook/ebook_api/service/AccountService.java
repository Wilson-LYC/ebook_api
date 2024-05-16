package com.ebook.ebook_api.service;

import com.ebook.ebook_api.dto.ResponseDto;

public interface AccountService {
    ResponseDto register(String email, String password, String captcha, String key);

    ResponseDto forgetPassword(String email, String password, String captcha, String key);
}
