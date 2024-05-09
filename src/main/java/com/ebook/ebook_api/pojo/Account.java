package com.ebook.ebook_api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account {
    private int id;
    private String email;
    private String password;
    private String name;
    private int roleId;
    private String avatar;
    private String phone;
    private String createTime;
    private String updateTime;
}
