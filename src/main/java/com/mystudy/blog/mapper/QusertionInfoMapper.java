package com.mystudy.blog.mapper;

import com.mystudy.blog.bean.QuestionInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QusertionInfoMapper {

    @Insert(value = "insert into question_info(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
    void insertQuestionInfo(QuestionInfo questionInfo);

    @Select(value = "select * from question_info")
    List<QuestionInfo> findAll();
}
