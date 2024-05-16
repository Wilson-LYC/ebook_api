package com.ebook.ebook_api.pojo;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @title: Article
 * @Author LuoZiHao
 * @Date: 2024/5/16 下午12:50
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("article")
public class Article {
    private int id;
    private String title;
    private String content;
    private int fid;
    private int uid;
    @TableField("createtime")
    private String createTime;
    @TableField("updatetime")
    private String updateTime;

    public JSONObject toJson() {
        return JSONObject.from(this);
    }
}
