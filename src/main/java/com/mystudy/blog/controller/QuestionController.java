package com.mystudy.blog.controller;

import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.QuestionDto;
import com.mystudy.blog.service.QuestionDtoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {

    @Resource
    QuestionDtoService service;

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
        model.addAttribute("question",questionDto);

        return "question";
    }
}
