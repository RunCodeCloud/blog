package com.mystudy.blog.mapper;

import com.mystudy.blog.bean.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment values(null,#{parent_id},#{type},#{commentator},#{gmt_create},#{gmt_modefied},#{like_count},#{content},#{question_id})")
    void insert(Comment comment);

    @Select("select * from comment where question_id=#{question_id}")
    List<Comment> findCommentByQuestionInfoId(Integer id);
}
