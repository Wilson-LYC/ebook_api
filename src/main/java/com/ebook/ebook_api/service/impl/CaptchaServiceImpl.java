package com.ebook.ebook_api.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailException;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String create() {
        int captcha= RandomUtil.randomInt(1000, 10000);
        return String.valueOf(captcha);
    }

    @Override
    public void save(String email, String captcha) {
        String key="captcha:"+email;
        redisTemplate.opsForValue().set(key,captcha);
        redisTemplate.expire(key,5*60,java.util.concurrent.TimeUnit.SECONDS);
    }

    @Override
    public JSONObject send(String email) {
        JSONObject res=new JSONObject();
        String captcha=this.create();//生成验证码
        this.save(email,captcha);//保存验证码
        String content="【公式E点通】用户您好！您的验证码是："+captcha+"，验证码5分钟内有效。请勿将验证码随意转发给他人！";
        try{
            MailUtil.send(email,"《公式E点通》验证码",content,false);//发送邮件
        }catch (MailException e){
            res.put("code",500);
            res.put("msg","邮件发送失败");
            res.put("error",e.getMessage());
            return res;
        }
        res.put("code",200);
        res.put("msg","邮件发送成功");
        return res;
    }
}
