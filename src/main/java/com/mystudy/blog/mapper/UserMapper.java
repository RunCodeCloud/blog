package com.mystudy.blog.mapper;

import com.mystudy.blog.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void insertUser(User user);
}
