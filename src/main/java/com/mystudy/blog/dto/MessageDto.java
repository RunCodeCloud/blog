package com.mystudy.blog.dto;

public class MessageDto {

    private Integer id;
    private String content;
    private Integer reply_id;
    private Integer operation;
    private Integer originator_id;
    private Integer commentator_id;
    private Integer status;

    private String reply_content;
    private String originator_name;
    private String commentator_name;

    private Integer question_id;

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReply_id() {
        return reply_id;
    }

    public void setReply_id(Integer reply_id) {
        this.reply_id = reply_id;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Integer getOriginator_id() {
        return originator_id;
    }

    public void setOriginator_id(Integer originator_id) {
        this.originator_id = originator_id;
    }

    public Integer getCommentator_id() {
        return commentator_id;
    }

    public void setCommentator_id(Integer commentator_id) {
        this.commentator_id = commentator_id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getOriginator_name() {
        return originator_name;
    }

    public void setOriginator_name(String originator_name) {
        this.originator_name = originator_name;
    }

    public String getCommentator_name() {
        return commentator_name;
    }

    public void setCommentator_name(String commentator_name) {
        this.commentator_name = commentator_name;
    }

}
