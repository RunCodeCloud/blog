package com.mystudy.blog.mapper;

import com.mystudy.blog.bean.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment values(null,#{parent_id},#{type},#{commentator},#{gmt_create},#{gmt_modefied},#{comment_count},#{dislike_count},#{like_count},#{content},#{question_id})")
    void insert(Comment comment);

    @Select("select * from comment where question_id=#{question_id} and type=1  order  by gmt_create Desc")
    List<Comment> findCommentByQuestionInfoId(Integer id);

    @Select("select * from comment where question_id=#{question_id} and type=2  order  by gmt_create Desc")
    List<Comment> findCommentByQuestionInfoIdAndType(Integer id);

    @Update("update comment set comment_count=#{comment_count}+1 where id=#{id}")
    void updateComment(Comment comment);

    @Update("update comment set dislike_count=#{dislike_count}+1 where id=#{id}")
    void updateDislike(Comment comment);
    @Update("update comment set dislike_count=#{dislike_count}-1 where id=#{id}")
    void updateDislikeFun(Comment comment);

    @Update("update comment set like_count=#{like_count}+1 where id=#{id}")
    void updateLike(Comment comment);
    @Update("update comment set like_count=#{like_count}-1 where id=#{id}")
    void updateLikeFun(Comment comment);

    @Select("select like_count from comment where id=#{id}")
    Integer selectLike(Integer id);

    @Select("select dislike_count from comment where id=#{id}")
    Integer selectDisLike(Integer id);

    @Select("select * from comment where id=#{id}")
    Comment selectById(Integer id);

    @Select("select * from comment where question_id=#{question_id} and type=2  order  by gmt_create Desc")
    List<Comment> selectSecondLevel(Integer question_id);
}
