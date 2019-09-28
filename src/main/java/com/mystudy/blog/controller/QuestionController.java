package com.mystudy.blog.controller;

import com.mystudy.blog.bean.Comment;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.QuestionDto;
import com.mystudy.blog.mapper.CommentMapper;
import com.mystudy.blog.service.QuestionDtoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {

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

        List<Comment> comments = commentMapper.findCommentByQuestionInfoId(questionDto.getId());

        model.addAttribute("comments",comments);
        return "question";
    }
}
