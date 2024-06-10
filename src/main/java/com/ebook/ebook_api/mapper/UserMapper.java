package com.ebook.ebook_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ebook.ebook_api.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where email = #{email} limit 1")
    User selectByEmail(String email);
    @Update("update user set name = #{name} where id = #{id}")
    void updateInfo(User user);
    @Update("update user set email = #{email} where id = #{id}")
    void updateEmail(User user);
}
