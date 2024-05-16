package com.ebook.ebook_api.service;

import com.ebook.ebook_api.dto.ResponseDto;

public interface CaptchaService {
    ResponseDto send_v1(String email);

    ResponseDto verify_v1(String captcha, String key);
    ResponseDto verify_v2(String email, String captcha);
    ResponseDto send_v2(String email);
}
