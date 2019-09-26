package com.mystudy.blog.exception;

import org.omg.IOP.RMICustomMaxStreamFormat;
import org.springframework.beans.factory.annotation.Autowired;

public enum ErrorEnum implements InterfaceException {

    QUESTION_NOT_FONUD("问题不存在,换个试试吧");

    private String message;

    private ErrorEnum(String message){
        this.message = message;
    }

    @Autowired
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
