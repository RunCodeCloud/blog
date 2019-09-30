package com.mystudy.blog.mapper;

import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.dto.QuestionDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface QusertionInfoMapper {

    @Insert(value = "insert into question_info(title,description,gmt_create,gmt_modified,creator,tag,comment_count,view_count,like_count) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag},#{comment_count},#{view_count},#{like_count})")
    void insertQuestionInfo(QuestionInfo questionInfo);

    @Select(value = "select * from question_info order by gmt_create Desc")
    List<QuestionInfo> findAll();

    @Select(value = "select * from question_info order  by gmt_create Desc limit #{offset},#{pageSize}")
    List<QuestionInfo> findByPage(Integer offset,Integer pageSize);

    @Select(value = "select * from question_info where creator=#{userId} order  by gmt_create Desc limit #{offset},#{pageSize}")
    List<QuestionInfo> findByUserIdAndLimit(Integer userId,Integer offset,Integer pageSize);

    @Select(value = "select * from question_info where creator=#{userId}  order  by gmt_create Desc")
    List<QuestionInfo> findByUserId(Integer userId);

    @Select(value = "select * from question_info where id=#{id}")
    QuestionInfo findById(Integer id);

    @Select(value = "select * from question_info where tag=#{tag} and id!=#{tagId} order  by gmt_create Desc")
    List<QuestionInfo> findByTag(Integer tagId,String tag);

    @Update(value = "update question_info set title=#{title},description=#{description},tag=#{tag} where id=#{id}")
    int updateById(String title,String description,String tag,Integer id);

    @Update(value = "update question_info set view_count = #{view_count}+1 where id=#{id}")
    int updateView(QuestionDto questionDto);

    @Update(value = "update question_info set comment_count = #{comment_count}+1 where id=#{id}")
    int updateComment(QuestionInfo questionInfo);

    @Select(value = "select * from question_info order  by comment_count Desc limit 0,5")
    List<QuestionInfo> findHostQuestion();

}
