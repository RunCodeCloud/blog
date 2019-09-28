package com.mystudy.blog.exception;

import org.omg.IOP.RMICustomMaxStreamFormat;
import org.springframework.beans.factory.annotation.Autowired;

public enum ErrorEnum implements InterfaceException {

    QUESTION_NOT_FONUD("问题不存在,换个试试吧"),
    USER_NOT_LOGIN("用户未登录"),

    COMMENT_NO_EXIST(20001,"评论不存在或已删除"),
    USER_NOT_LOGIN_T(20002,"用户未登录");
    ;

    private ErrorEnum(String message){
        this.message = message;
    }
    private ErrorEnum(Integer errorCode,String message){
        this.errorCode = errorCode;
        this.message = message;
    }

    private String message;
    private Integer errorCode;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
