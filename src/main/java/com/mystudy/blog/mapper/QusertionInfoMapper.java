package com.mystudy.blog.mapper;

import com.mystudy.blog.bean.QuestionInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QusertionInfoMapper {

    @Insert(value = "insert into question_info(title,description,gmt_create,gmt_modified,creator,tag,comment_count,view_count,like_count) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag},#{comment_count},#{view_count},#{like_count})")
    void insertQuestionInfo(QuestionInfo questionInfo);

    @Select(value = "select * from question_info")
    List<QuestionInfo> findAll();

    @Select(value = "select * from question_info limit #{offset},#{pageSize}")
    List<QuestionInfo> findByPage(Integer offset,Integer pageSize);

    @Select(value = "select * from question_info where creator=#{userId} limit #{offset},#{pageSize}")
    List<QuestionInfo> findByUserIdAndLimit(Integer userId,Integer offset,Integer pageSize);

    @Select(value = "select * from question_info where creator=#{userId}")
    List<QuestionInfo> findByUserId(Integer userId);

    @Select(value = "select * from question_info where id=#{id}")
    QuestionInfo findById(Integer id);

    @Update(value = "update question_info set title=#{title},description=#{description},tag=#{tag} where id=#{id}")
    void updateById(String title,String description,String tag,Integer id);
}
