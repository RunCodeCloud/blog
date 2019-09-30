package com.mystudy.blog.bean;

public class Message {

    private Integer id;
    private Integer content;
    private Integer reply_id;
    private Integer operation;
    private Integer originator_id;
    private Integer commentator_id;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
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

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content=" + content +
                ", reply_id=" + reply_id +
                ", operation=" + operation +
                ", originator_id=" + originator_id +
                ", commentator_id=" + commentator_id +
                ", status=" + status +
                '}';
    }

}
