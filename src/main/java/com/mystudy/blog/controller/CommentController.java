package com.mystudy.blog.controller;

import com.mystudy.blog.bean.Comment;
import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.CommentDto;
import com.mystudy.blog.error.ErrorHandler;
import com.mystudy.blog.exception.ErrorEnum;
import com.mystudy.blog.exception.MyException;
import com.mystudy.blog.mapper.CommentMapper;
import com.mystudy.blog.mapper.QusertionInfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController extends ErrorHandler {

    @Resource
    QusertionInfoMapper qusertionInfoMapper;

    @Resource
    CommentMapper commentMapper;

    @RequestMapping(value = "/comment",method = {RequestMethod.POST})
    @ResponseBody
    @Transactional
    public Object comment(CommentDto commentDto,
                          HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        Map<String,String> map = new HashMap<>();

        if(user==null){
            map.put("status","fail");
            map.put("message","用户未登录");
            return map;
        }

        Integer  parent_id = commentDto.getParent_id();
        String content = commentDto.getContent();
        String type = commentDto.getType();

        if(parent_id==null){
            map.put("status","fail");
            map.put("message","评论内容不存在，或已删除");
           return map;
        }
        if(content==null){
            map.put("status","fail");
            map.put("message","评论内容不能为空");
            return map;
        }
        if(type==null){
            map.put("status","fail");
            map.put("message","评论失败");
            return map;
        }

        Comment comment = new Comment();
        comment.setCommentator(user.getId());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modefied(System.currentTimeMillis());
        comment.setLike_count(0);
        BeanUtils.copyProperties(commentDto,comment);
        commentMapper.insert(comment);

        QuestionInfo info = qusertionInfoMapper.findById(comment.getQuestion_id());
        int result =  qusertionInfoMapper.updateComment(info);

        //回复评论
        //回复问题
        map.put("status","success");
        String url = "http://localhost:8080/question/"+info.getId();
        map.put("url",url);
        return map;

    }
}
