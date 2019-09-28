package com.mystudy.blog.controller;

import com.mystudy.blog.bean.Comment;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.CommentDto;
import com.mystudy.blog.dto.QuestionDto;
import com.mystudy.blog.mapper.CommentMapper;
import com.mystudy.blog.mapper.UserMapper;
import com.mystudy.blog.service.QuestionDtoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Resource
    UserMapper userMapper;

    @Resource
    QuestionDtoService service;
    @Resource
    CommentMapper commentMapper;

    @RequestMapping(value = "/question/{id}",method = RequestMethod.GET)
    public String question(@PathVariable(name = "id")Integer id,
                           Model model,
                           HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            return "hello";
        }

        QuestionDto questionDto = service.findById(id);
        questionDto.setUser(user);

        //累加评论
        service.updateView(questionDto);
        model.addAttribute("question",questionDto);

        //还有一个问题，就是多级回复的问题
        //同样的解决方式，首先加载一级回复，点击回复框框后应该jia加载的
        // 是根据这个回复的id加载出它回复的内容
        List<Comment> comments = commentMapper.findCommentByQuestionInfoId(questionDto.getId());
        List<CommentDto> list = convertFromComment(comments);

        model.addAttribute("comments",list);
        return "question";
    }


    private List<CommentDto> convertFromComment(List<Comment> list){

        if(list==null){
            return null;
        }

        List<CommentDto> dtoList = new ArrayList<>();
        for(Comment comment:list){
            User user = userMapper.findById(comment.getCommentator());
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setFail(user);
            dtoList.add(commentDto);
        }

        return dtoList;
    }

}
