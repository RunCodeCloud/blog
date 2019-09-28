package com.mystudy.blog.dto;

import com.mystudy.blog.bean.User;

public class CommentDto {

    private Integer id;
    private Integer parent_id;
    private String type;
    private String content;
    private Integer question_id;
    private Integer commentator;
    private Long gmt_create;
    private Long gmt_modefied;
    private Integer like_count;
    private Integer comment_count;
    private User fail;
    private Integer dislike_count;

    public Integer getDislike_count() {
        return dislike_count;
    }

    public void setDislike_count(Integer dislike_count) {
        this.dislike_count = dislike_count;
    }

    public User getFail() {
        return fail;
    }

    public void setFail(User fail) {
        this.fail = fail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public Integer getCommentator() {
        return commentator;
    }

    public void setCommentator(Integer commentator) {
        this.commentator = commentator;
    }

    public Long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Long getGmt_modefied() {
        return gmt_modefied;
    }

    public void setGmt_modefied(Long gmt_modefied) {
        this.gmt_modefied = gmt_modefied;
    }

    public Integer getLike_count() {
        return like_count;
    }

    public void setLike_count(Integer like_count) {
        this.like_count = like_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }



    @Override
    public String toString() {
        return "CommentDto{" +
                "parent_id=" + parent_id +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", question_id=" + question_id +
                '}';
    }
}
