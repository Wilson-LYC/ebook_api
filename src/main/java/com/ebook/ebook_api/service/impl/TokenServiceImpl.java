package com.ebook.ebook_api.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ebook.ebook_api.dto.ResponseDto;
import com.ebook.ebook_api.mapper.AccountMapper;
import com.ebook.ebook_api.pojo.Account;
import com.ebook.ebook_api.service.CaptchaService;
import com.ebook.ebook_api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    CaptchaService captchaService;
    /**
     * 生成token
     * @param account 帐号信息
     * @return token
     */
    private String create(Account account){
        return JWT.create()
                .withClaim("id",account.getId())
                .withClaim("role",account.getRole())
                .withExpiresAt(DateUtil.offsetDay(DateUtil.date(),5))
                .sign(Algorithm.HMAC256("ebook@2024"));
    }

    /**
     * 验证token
     * @param token token
     * @return 验证结果
     */
    @Override
    public JSONObject verify(String token){
        JSONObject res=new JSONObject();
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("ebook@2024")).build();
        try{
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            res.put("code",200);
            res.put("msg","验证通过");
            JSONObject data=new JSONObject();
            JSONObject account=new JSONObject();
            account.put("id",decodedJWT.getClaim("id").asInt());
            account.put("roleId",decodedJWT.getClaim("roleId").asInt());
            data.put("account",account);
            res.put("data",data);
        }catch (TokenExpiredException e){
            res.put("code",400);
            res.put("msg","token已过期");
        }catch (Exception e){
            res.put("code",400);
            res.put("msg","token非法");
        }
        return res;
    }

    /**
     * 密码登录
     * @param email 邮箱
     * @param password 密码
     * @return 登录结果
     */
    @Override
    public ResponseDto loginByPwd(String email, String password) {
        Account account = accountMapper.selectByEmail(email);
        if (account == null) {
            return new ResponseDto(400, "用户不存在");
        }
        if (!account.getPassword().equals(password)) {
            return new ResponseDto(400, "密码错误");
        }
        String token = create(account);
        JSONObject data = new JSONObject();
        data.put("token", token);
        data.put("user", account.toJson());
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
        Account account = accountMapper.selectByEmail(email);
        if (account == null) {
            return new ResponseDto(400, "用户不存在");
        }
        //获取验证码
        if (captchaService.verify_v2(email,captcha).getCode() != 200){
            return new ResponseDto(400, "验证码错误");
        }
        //生成token
        String token = create(account);
        JSONObject data = new JSONObject();
        data.put("token", token);
        data.put("user", account.toJson());
        return new ResponseDto(200, "登录成功", data);
    }
}
