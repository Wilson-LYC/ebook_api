package com.ebook.ebook_api.pojo;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @title: Function
 * @Author LuoZiHao
 * @Date: 2024/5/16 下午2:03
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("function")
public class Function {
    private int id;
    private int cid; //类别id
    private String name;
    private String intro; //功能简介
    private String tutorial; //使用教程
    private String video_url; //教学视频

    @TableField("createtime")
    private String createTime;
    @TableField("updatetime")
    private String updateTime;

    public JSONObject toJson() {
        return JSONObject.from(this);
    }
}
