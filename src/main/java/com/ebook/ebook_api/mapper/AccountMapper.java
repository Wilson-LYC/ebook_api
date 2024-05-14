package com.ebook.ebook_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ebook.ebook_api.pojo.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    @Select("select * from account where email = #{email} limit 1")
    Account selectByEmail(String email);
}
