package com.mystudy.blog.service;

import com.mystudy.blog.bean.User;
import com.mystudy.blog.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public User findUserByAccountId(String id){

        User user = userMapper.findUserByAccountId(id);
        return user;
    }
}
