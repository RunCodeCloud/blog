package com.mystudy.blog.controller;

import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.QuestionDto;
import com.mystudy.blog.mapper.UserMapper;
import com.mystudy.blog.service.QuestionDtoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LoginController {

    @Resource
    QuestionDtoService service;

    @Resource
    UserMapper userMapper;

    @RequestMapping(value = "/hello",method = {RequestMethod.GET})
    public String hello(HttpServletRequest request,
                        Model model){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies){
            if("token".equals(cookie.getName())){
                User user = userMapper.findByToken(cookie.getValue());
                request.getSession().setAttribute("user",user);
                break;
            }
        }

        List<QuestionDto> list = service.findAll();
        model.addAttribute("questions",list);

        return "index";
    }
}
