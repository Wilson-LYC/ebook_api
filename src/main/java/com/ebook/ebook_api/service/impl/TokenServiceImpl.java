package com.ebook.ebook_api.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ebook.ebook_api.dto.ResponseDto;
import com.ebook.ebook_api.mapper.UserMapper;
import com.ebook.ebook_api.pojo.User;
import com.ebook.ebook_api.service.CaptchaService;
import com.ebook.ebook_api.service.TokenService;
import com.ebook.ebook_api.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    CaptchaService captchaService;

    /**
     * 密码登录
     * @param email 邮箱
     * @param password 密码
     * @return 登录结果
     */
    @Override
    public ResponseDto loginByPwd(String email, String password) {
        User user;
        try{
            user = userMapper.selectByEmail(email);
        }catch (Exception e){
            log.error("[TokenService] loginByPwd:查找用户时错误\n"+e.getMessage());
            return new ResponseDto(500, "服务器错误");
        }
        if (user == null) {
            return new ResponseDto(400, "用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            return new ResponseDto(400, "密码错误");
        }
        String token = TokenUtil.create(user);
        JSONObject data = new JSONObject();
        data.put("token", token);
        data.put("user", user.toJson());
        return new ResponseDto(200, "登录成功", data);
    }

    /**
     * 验证码登录
     * @param email 邮箱
     * @param captcha 验证码
     * @return 登录结果
     */
    @Override
    public ResponseDto loginByCaptcha(String email, String captcha) {
        //检查用户是否存在
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            return new ResponseDto(400, "用户不存在");
        }
        //获取验证码
        if (captchaService.verify(email,captcha).getCode() != 200){
            return new ResponseDto(400, "验证码错误");
        }
        //生成token
        String token = TokenUtil.create(user);
        JSONObject data = new JSONObject();
        data.put("token", token);
        data.put("user", user.toJson());
        return new ResponseDto(200, "登录成功", data);
    }
}
