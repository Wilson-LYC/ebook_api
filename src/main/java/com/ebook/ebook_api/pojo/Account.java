package com.ebook.ebook_api.pojo;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("account")
public class Account {
    private int id;
    private String email;
    private String password;
    private String name="请设置昵称";
    private String role="user";
    private String avatar="https://tdesign.gtimg.com/site/avatar.jpg";
    private String phone;
    @TableField("createtime")
    private String createTime;
    @TableField("updatetime")
    private String updateTime;

    public JSONObject toJson() {
        this.setPassword(null);
        return JSONObject.from(this);
    }
}
