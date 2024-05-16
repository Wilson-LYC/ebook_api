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
     * 生成邮件内容 v1
     * @param captcha 验证码
     * @return 邮件内容
     */
    private String getContent_v1(String captcha){
        return "【公式E点通】用户您好！您的验证码是："+captcha+"。";
    }

    /**
     * 生成邮件内容 v2
     * @param captcha 验证码
     * @return 邮件内容
     */
    private String getContent_v2(String captcha){
        return "【公式E点通】用户您好！您的验证码是："+captcha+"。有效期5分钟。";
    }

    /**
     * 发送验证码至邮箱 v1
     * @param email 邮箱
     * @return 返回发送结果
     */
    @Override
    public ResponseDto send_v1(String email) {
        //生成验证码
        JSONObject captchaJson=this.create();
        //生成邮件内容
        String content=this.getContent_v1(captchaJson.getString("captcha"));
        //发送邮件
        try{
            MailUtil.send(email,"公式E点通",content,false);
        }catch (MailException e){
            log.error("邮箱验证码发送失败",e);
            return new ResponseDto(500,"邮件发送失败，请检查邮箱是否正确");
        }
        JSONObject data=new JSONObject();
        data.put("key",captchaJson.getString("key"));
        data.put("tips","核验验证码时，验证码和key需要一同传递");
        return new ResponseDto(200,"邮件发送成功",data);
    }

    /**
     * 验证验证码 v1
     * @param captcha 验证码
     * @param key 验证码key
     * @return 返回验证结果
     */
    @Override
    public ResponseDto verify_v1(String captcha, String key) {
        String temp;
        try{
            temp=sm4.decryptStr(key, CharsetUtil.CHARSET_UTF_8);
        }catch (CryptoException e){
            log.error("验证码key解密错误",e);
            return new ResponseDto(400,"请重新获取验证码");
        }
        if(temp.equals(captcha)){
            return new ResponseDto(200,"验证码正确");
        }else{
            return new ResponseDto(400,"验证码错误");
        }
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
    private String getCaptcha(String email){
        String key="captcha:"+email;
        //检查当前key是否存在
        if(!redisTemplate.hasKey(key)){
            return null;
        }
        return (String) redisTemplate.opsForValue().get(key);
    }
    @Override
    public ResponseDto send_v2(String email) {
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
        String content=this.getContent_v2(captcha.getString("captcha"));
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
     * 验证验证码 v2
     * @param email 邮箱
     * @param captcha 验证码
     * @return 返回验证结果
     */
    @Override
    public ResponseDto verify_v2(String email, String captcha) {
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
