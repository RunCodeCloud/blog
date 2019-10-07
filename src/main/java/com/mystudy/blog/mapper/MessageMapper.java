package com.mystudy.blog.mapper;

import com.mystudy.blog.bean.Message;
import com.mystudy.blog.bean.QuestionInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MessageMapper {

    @Insert("insert into message values(null,#{content},#{reply_id},#{operation},#{originator_id},#{status},#{gmt_create},#{zuile})")
    void insert(Message message);

    @Select("select * from message where originator_id=#{originator_id}")
    List<Message> selectAllById(Integer originator_id);

    @Select("select * from message where reply_id=#{reply_id} and operation=#{-1}")
    Message selectByReplyId(Integer originator_id,Integer operation);

    @Update("update message set status=#{status} where id=#{id}")
    void updateStatus(Integer status,Integer id);


    @Select(value = "select * from message where originator_id=#{userId} order  by gmt_create Desc limit #{offset},#{pageSize}")
    List<Message> findByUserIdAndLimit(Integer userId, Integer offset, Integer pageSize);

    @Select(value = "select * from message where status=0")
    List<Message> findStatus();
}
