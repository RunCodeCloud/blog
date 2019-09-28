package com.mystudy.blog.service;

import com.mystudy.blog.bean.Comment;
import com.mystudy.blog.exception.ErrorEnum;
import com.mystudy.blog.exception.MyException;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public void insert(Comment comment){
        if(comment.getParent_id()==null||comment.getParent_id()==0){
            throw new MyException(ErrorEnum.COMMENT_NO_EXIST);
        }
    }
}
