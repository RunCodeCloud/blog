package com.mystudy.blog.dto;

public class CommentDto {

    private Integer parent_id;
    private String content;
    private String type;
    private Integer question_id;

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
