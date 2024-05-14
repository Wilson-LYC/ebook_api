package com.ebook.ebook_api.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.mapper.AccountMapper;
import com.ebook.ebook_api.pojo.Account;
import com.ebook.ebook_api.service.AccountService;
import com.ebook.ebook_api.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    CaptchaService captchaService;
    @Autowired
    AccountMapper accountMapper;
    /**
     * 注册
     * @param email 邮箱
     * @param password 密码
     * @param captcha 验证码
     * @param key 验证码key
     * @return 注册结果
     */
    @Override
    public JSONObject register(String email, String password, String captcha, String key) {
        JSONObject res = new JSONObject();
        // 验证码校验
        JSONObject captchaRes = captchaService.verify(captcha, key);
        if (captchaRes.getInteger("code") != 200){
            return captchaRes;
        }
        // 检查邮箱是否已被注册
        if (accountMapper.selectByEmail(email) != null){
            res.put("code", 400);
            res.put("msg", "邮箱已被注册");
            return res;
        }
        try{
            Account account =new Account();
            account.setEmail(email);
            account.setPassword(password);
            account.setName(email);
            accountMapper.insert(account);
        }catch (Exception e){
            res.put("code", 500);
            res.put("msg", "注册失败");
            log.error("注册帐号时，mysql错误",e);
            return res;
        }
        res.put("code", 200);
        res.put("msg", "注册成功");
        return res;
    }
}