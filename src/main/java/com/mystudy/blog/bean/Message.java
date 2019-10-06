package com.mystudy.blog.bean;

public class Message {

    private Integer id;
    private String content;
    private Integer reply_id;
    private Integer operation;
    private Integer originator_id;
    private Long gmt_create;
    private Integer status;
    private Integer zuile;

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

    public Long getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getZuile() {
        return zuile;
    }

    public void setZuile(Integer zuile) {
        this.zuile = zuile;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", reply_id=" + reply_id +
                ", operation=" + operation +
                ", originator_id=" + originator_id +
                ", gmt_create=" + gmt_create +
                ", status=" + status +
                ", zuile=" + zuile +
                '}';
    }
}
