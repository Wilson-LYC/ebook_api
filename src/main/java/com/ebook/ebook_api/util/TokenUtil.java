package com.ebook.ebook_api.util;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ebook.ebook_api.pojo.User;

public class TokenUtil {
    private final static String SECRET = "ebook@2024";
    /**
     * 生成token
     * @param user 帐号信息
     * @return token
     */
    public static String create(User user){
        return JWT.create()
                .withClaim("id", user.getId())
                .withExpiresAt(DateUtil.offsetDay(DateUtil.date(),5))
                .sign(Algorithm.HMAC256(SECRET));
    }
    /**
     * 解码token
     * @param token token
     * @return 验证结果
     */
    public static JSONObject decode(String token){
        JSONObject res=new JSONObject();
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        try{
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            res.put("code",200);
            res.put("msg","成功");
            res.put("uid",decodedJWT.getClaim("id").asInt());
        }catch (TokenExpiredException e){
            res.put("code",400);
            res.put("msg","已过期");
        }catch (Exception e){
            res.put("code",400);
            res.put("msg","失效");
        }
        return res;
    }

}
