package com.ebook.ebook_api.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ebook.ebook_api.pojo.Account;
import com.ebook.ebook_api.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;


@Service
public class TokenServiceImpl implements TokenService {
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

    @Override
    public JSONObject login(String email, String password) {
        JSONObject res=new JSONObject();
        //模拟数据库查询
        Account account=new Account();
        account.setId(1);
        account.setEmail(email);
        String token=create(account);
        res.put("code",200);
        res.put("msg","登录成功");
        JSONObject data=new JSONObject();
        data.put("account",account.toJson());
        data.put("token",token);
        res.put("data",data);
        return res;
    }
}
