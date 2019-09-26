package com.mystudy.blog.mapper;

import com.mystudy.blog.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user_info(account_id,name,token,gmt_create,gmt_modified,img_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{getModified},#{img_url})")
    void insertUser(User user);

    @Select("select * from user_info where token=#{token}")
    User findByToken(@Param(value = "token") String token);

    @Select("select * from user_info where id=#{creator}")
    User findById(@Param(value = "creator") Integer creator);

    @Select("select * from user_info where account_id=#{id}")
    User findUserByAccountId(@Param(value = "id") String id);
}
