package com.ebook.ebook_api.dto;

import com.alibaba.fastjson2.JSONObject;

public class ResponseDto extends JSONObject {
    public ResponseDto(int code, String msg) {
        super();
        this.put("code", code);
        this.put("msg", msg);
    }
    public ResponseDto(int code, String msg,JSONObject data) {
        super();
        this.put("code", code);
        this.put("msg", msg);
        this.put("data", data);
    }
}
