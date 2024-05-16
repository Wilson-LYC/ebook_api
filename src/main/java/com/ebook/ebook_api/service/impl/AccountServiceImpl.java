package com.ebook.ebook_api.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ebook.ebook_api.dto.ResponseDto;
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
    public ResponseDto register(String email, String password, String captcha, String key) {
        // 验证码校验
        if (captchaService.verify_v1(captcha, key).getInteger("code") != 200){
            return new ResponseDto(400, "验证码错误");
        }
        // 检查邮箱是否已被注册
        if (accountMapper.selectByEmail(email) != null){
            return new ResponseDto(400, "邮箱已被注册");
        }
        // 注册
        try{
            Account account =new Account();
            account.setEmail(email);
            account.setPassword(password);
            accountMapper.insert(account);
        }catch (Exception e){
            log.error("注册帐号时，mysql错误",e);
            return new ResponseDto(500, "系统错误，注册失败");
        }
        return new ResponseDto(200, "注册成功");
    }

    /**
     * 忘记密码(重置密码)
     * @param email 邮箱
     * @param password 新密码
     * @param captcha 验证码
     * @param key 验证码key
     * @return 重置密码结果
     */
    @Override
    public ResponseDto forgetPassword(String email, String password, String captcha, String key) {
        // 验证码校验
        if (captchaService.verify_v1(captcha, key).getInteger("code") != 200){
            return new ResponseDto(400, "验证码错误");
        }
        // 查找用户
        Account account = accountMapper.selectByEmail(email);
        if (account == null){
            return new ResponseDto(400, "邮箱未注册");
        }
        // 重置密码
        try{
            account.setPassword(password);
            this.updateAccount(account);
        }catch (Exception e){
            log.error("重置密码时，mysql错误",e);
            return new ResponseDto(500, "系统错误，重置密码失败");
        }
        return new ResponseDto(200, "重置密码成功");
    }

    /**
     * 更新帐号信息
     * @param account 帐号信息
     */
    private void updateAccount(Account account) {
        UpdateWrapper<Account> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", account.getId());
        accountMapper.update(account,updateWrapper);
    }
}