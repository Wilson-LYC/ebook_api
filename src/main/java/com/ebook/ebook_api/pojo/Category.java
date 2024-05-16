package com.ebook.ebook_api.pojo;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @title: Category
 * @Author LuoZiHao
 * @Date: 2024/5/16 下午1:52
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("category")
public class Category {
    private int id;
    private String name;
    private String icon;
    private String intro;
    @TableField("createtime")
    private String createTime;
    @TableField("updatetime")
    private String updateTime;

    public JSONObject toJson() {
        return JSONObject.from(this);
    }
}
