package com.ebook.ebook_api.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.extra.mail.MailException;
import cn.hutool.extra.mail.MailUtil;
import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.dto.ResponseDto;
import com.ebook.ebook_api.service.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    RedisTemplate redisTemplate;

    SymmetricCrypto sm4 = SmUtil.sm4();

    /**
     * 生成验证码
     * @return 验证码和key
     */
    private JSONObject create() {
        JSONObject res=new JSONObject();
        String captcha= String.valueOf(RandomUtil.randomInt(1000, 10000));
        res.put("captcha",captcha);
        String key=sm4.encryptHex(captcha);
        res.put("key",key);
        return res;
    }

    /**
     * 生成邮件内容
     * @param captcha 验证码
     * @return 邮件内容
     */
    private String getContent(String captcha){
        return "【公式E点通】用户您好！您的验证码是："+captcha+"。有效期5分钟。";
    }

    /**
     * 保存验证码
     * @param email 邮箱
     * @param captcha 验证码
     */
    private void saveCaptcha(String email,String captcha){
        String key="captcha:"+email;
        //检查当前key是否存在
        if(redisTemplate.hasKey(key)){
            redisTemplate.delete(key);
        }
        redisTemplate.opsForValue().set(key,captcha);
        //设置过期时间 5分钟
        redisTemplate.expire(key,300,java.util.concurrent.TimeUnit.SECONDS);
    }

    /**
     * 获取验证码
     * @param email 邮箱
     * @return 验证码
     */
    private String getCaptcha(String email){
        String key="captcha:"+email;
        //检查当前key是否存在
        if(!redisTemplate.hasKey(key)){
            return null;
        }
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 发送验证码
     * @param email 邮箱
     * @return 返回发送结果
     */
    @Override
    public ResponseDto send(String email) {
        //生成验证码
        JSONObject captcha= this.create();
        //保存验证码
        try {
            this.saveCaptcha(email,captcha.getString("captcha"));
        }catch (Exception e){
            log.error("验证码保存失败",e);
            return new ResponseDto(500,"系统错误，请稍后再试");
        }
        //生成邮件内容
        String content=this.getContent(captcha.getString("captcha"));
        //发送邮件
        try{
            MailUtil.send(email,"公式E点通",content,false);
        }catch (MailException e){
            log.error("邮箱验证码发送失败",e);
            return new ResponseDto(500,"邮件发送失败，请检查邮箱是否正确");
        }
        return new ResponseDto(200,"验证码发送成功");
    }

    /**
     * 验证验证码
     * @param email 邮箱
     * @param captcha 验证码
     * @return 返回验证结果
     */
    @Override
    public ResponseDto verify(String email, String captcha) {
        //获取验证码
        String temp=this.getCaptcha(email);
        if(temp==null){
            return new ResponseDto(400,"验证码错误");
        }
        if(temp.equals(captcha)){
            return new ResponseDto(200,"验证码正确");
        }else{
            return new ResponseDto(400,"验证码错误");
        }
    }
}
