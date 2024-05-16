package com.ebook.ebook_api.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.extra.mail.MailException;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {
    SymmetricCrypto sm4 = SmUtil.sm4();
    /**
     * 生成验证码
     * @return 验证码
     */
    private JSONObject create() {
        JSONObject res=new JSONObject();
        String captcha= String.valueOf(RandomUtil.randomInt(1000, 10000));
        res.put("captcha",captcha);
        String key=sm4.encryptHex(captcha);
        res.put("key",key);
        return res;
    }

    private String getContent(String captcha){
        return "【公式E点通】用户您好！您的验证码是："+captcha+"。请勿将验证码随意转发给他人！";
    }

    /**
     * 发送验证码至邮箱
     * @param email 邮箱
     * @return 返回发送结果
     */
    @Override
    public JSONObject send(String email) {
        JSONObject res=new JSONObject();
        JSONObject captchaJson=this.create();
        String content=this.getContent(captchaJson.getString("captcha"));
        try{
            MailUtil.send(email,"公式E点通",content,false);//发送邮件
        }catch (MailException e){
            res.put("code",500);
            res.put("msg","验证码发送失败");
            log.error("邮箱验证码发送失败",e);
            return res;
        }
        res.put("code",200);
        res.put("msg","验证码发送成功");
        JSONObject data=new JSONObject();
        data.put("key",captchaJson.getString("key"));
        res.put("data",data);
        return res;
    }

    /**
     * 验证验证码
     * @param captcha 验证码
     * @param key 验证码key
     * @return 返回验证结果
     */
    @Override
    public JSONObject verify(String captcha, String key) {
        JSONObject res=new JSONObject();
        String temp;
        try{
            temp=sm4.decryptStr(key, CharsetUtil.CHARSET_UTF_8);
        }catch (CryptoException e){
            res.put("code",500);
            res.put("msg","请重新获取验证码");
            log.error("验证码key格式错误",e);
            return res;
        }
        if(temp.equals(captcha)){
            res.put("code",200);
            res.put("msg","验证码正确");
        }else{
            res.put("code",500);
            res.put("msg","验证码错误");
        }
        return res;
    }
}
