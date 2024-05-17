package com.ebook.ebook_api.service;

import com.ebook.ebook_api.dto.ResponseDto;

public interface CaptchaService {
    ResponseDto verify(String email, String captcha);
    ResponseDto send(String email);
}
