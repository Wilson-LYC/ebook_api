package com.ebook.ebook_api.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ebook.ebook_api.dto.ResponseDto;
import com.ebook.ebook_api.mapper.UserMapper;
import com.ebook.ebook_api.pojo.User;
import com.ebook.ebook_api.service.UserService;
import com.ebook.ebook_api.service.CaptchaService;
import com.ebook.ebook_api.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    CaptchaService captchaService;
    @Autowired
    UserMapper userMapper;

    /**
     * 注册
     * @param email 邮箱
     * @param password 密码
     * @param captcha 验证码
     * @return 注册结果
     */
    @Override
    public ResponseDto register(String email, String password, String captcha) {
        // 检查邮箱是否已被注册
        if (userMapper.selectByEmail(email) != null){
            return new ResponseDto(400, "邮箱已被注册");
        }
        // 验证码校验
        if (captchaService.verify(email, captcha).getCode() != 200){
            return new ResponseDto(400, "验证码错误");
        }
        // 注册
        try{
            User user =new User();
            user.setEmail(email);
            user.setPassword(password);
            userMapper.insert(user);
        }catch (Exception e){
            log.error("注册帐号时，mysql遇到错误",e);
            return new ResponseDto(500, "系统错误，注册失败");
        }
        return new ResponseDto(200, "注册成功");
    }

    /**
     * 通过token获取用户信息
     * @param token token
     * @return 用户信息
     */
    @Override
    public ResponseDto getUserByToken(String token) {
        // token解码
        JSONObject tokenDecode = TokenUtil.decode(token);
        if (tokenDecode.getInteger("code") != 200){
            return new ResponseDto(400, "登录过期");
        }
        int uid= tokenDecode.getInteger("uid");
        // 查找用户
        User user;
        try{
            user = userMapper.selectById(uid);
        }catch (Exception e){
            log.error("通过token获取用户信息时，mysql错误",e);
            return new ResponseDto(500, "系统错误，用户信息获取失败");
        }
        if (user == null){
            return new ResponseDto(400, "用户不存在");
        }
        JSONObject data = new JSONObject();
        data.put("user", user.toJson());
        return new ResponseDto(200, "获取成功",data);
    }

    @Override
    public ResponseDto updateUserInfo(String token, int id, String name) {
        JSONObject tokenDecode = TokenUtil.decode(token);
        if (tokenDecode.getInteger("code") != 200){
            return new ResponseDto(400, "登录过期");
        }
        // 更新用户信息
        User user=new User();
        user.setId(id);
        user.setName(name);
        try{
            userMapper.updateInfo(user);
        }catch (Exception e){
            log.error("更新用户资料时，mysql错误",e);
            return new ResponseDto(500, "系统错误");
        }
        return new ResponseDto(200, "更新成功");
    }

    /**
     * 重置密码
     * @param email 邮箱
     * @param password 新密码
     * @param captcha 验证码
     * @return 重置密码结果
     */
    @Override
    public ResponseDto resetPassword(String email, String password, String captcha) {
        // 验证码校验
        if (captchaService.verify(email,captcha).getCode() != 200){
            return new ResponseDto(400, "验证码错误");
        }
        // 查找用户
        User user = userMapper.selectByEmail(email);
        if (user == null){
            return new ResponseDto(400, "邮箱未注册");
        }
        // 重置密码
        try{
            user.setPassword(password);
            this.updateAccount(user);
        }catch (Exception e){
            log.error("重置密码时，mysql错误",e);
            return new ResponseDto(500, "系统错误");
        }
        return new ResponseDto(200, "重置成功");
    }

    @Override
    public ResponseDto updateEmail(String token, int id, String email, String captcha) {
        JSONObject tokenDecode = TokenUtil.decode(token);
        if (tokenDecode.getInteger("code") != 200){
            return new ResponseDto(400, "登录过期");
        }
        // 验证码校验
        if (captchaService.verify(email,captcha).getCode() != 200){
            return new ResponseDto(400, "验证码错误");
        }
        // 更新邮箱
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        try{
            userMapper.updateEmail(user);
        }catch (Exception e){
            log.error("更新邮箱时，mysql错误",e);
            return new ResponseDto(500, "系统错误");
        }
        return new ResponseDto(200, "更新成功");
    }

    /**
     * 更新帐号信息
     * @param user 帐号信息
     */
    private void updateAccount(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", user.getId());
        userMapper.update(user,updateWrapper);
    }


}