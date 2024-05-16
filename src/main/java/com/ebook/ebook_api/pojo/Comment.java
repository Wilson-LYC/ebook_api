package com.ebook.ebook_api.pojo;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @title: Comment
 * @Author LuoZiHao
 * @Date: 2024/5/16 下午1:54
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("comment")
public class Comment {
    private int id;
    private int aid; //文章id
    private int cid; //评论id
    private int re_cid; //回复评论id
    private int uid; //评论者id

    private String content;
    @TableField("createtime")
    private String createTime;
    @TableField("updatetime")
    private String updateTime;


    public JSONObject toJson() {
        return JSONObject.from(this);
    }
}
