package com.ebook.ebook_api.pojo;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @title: Comment_like
 * @Author LuoZiHao
 * @Date: 2024/5/16 下午2:23
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment_like {
    private int cid;
    private int uid;
    private String createTime;


    public JSONObject toJson() {
        return JSONObject.from(this);
    }
}
