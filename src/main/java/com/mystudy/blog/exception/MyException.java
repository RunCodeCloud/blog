package com.mystudy.blog.exception;

import java.util.UUID;

public class MyException extends RuntimeException{

    private String message;

    public MyException(ErrorEnum errorEnum){
        this.message = errorEnum.getMessage();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public MyException(String message){
        super(message);
        this.message = message;
    }

}
