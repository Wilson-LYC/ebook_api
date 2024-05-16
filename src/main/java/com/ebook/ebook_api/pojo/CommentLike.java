package com.ebook.ebook_api.pojo;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentLike {
    private int cid;
    private int uid;
    private String createTime;


    public JSONObject toJson() {
        return JSONObject.from(this);
    }
}
