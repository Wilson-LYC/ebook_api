package com.ebook.ebook_api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ebook.ebook_api.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
