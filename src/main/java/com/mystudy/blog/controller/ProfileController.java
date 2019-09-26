package com.mystudy.blog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.QuestionDto;
import com.mystudy.blog.mapper.QusertionInfoMapper;
import com.mystudy.blog.mapper.UserMapper;
import com.mystudy.blog.service.QuestionDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {

    @Resource
    UserMapper userMapper;

    @Resource
    QusertionInfoMapper qusertionInfoMapper;

    @Resource
    QuestionDtoService service;


    @RequestMapping(value = "/profile/{action}",method = RequestMethod.GET)
    public String profile(@PathVariable(value = "action")String action,
                          @RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo,
                          @RequestParam(value = "pageSize",defaultValue="1")Integer pageSize,
                          HttpServletRequest request,
                          Model model){

        User user = (User) request.getSession().getAttribute("user");

        if(user==null){
            return "hello";
        }

        if("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("secionName","我的提问");
        }else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("secionName","最新回复");
        }

        List<QuestionDto> list = service.findByPage(user.getId(),pageNo,pageSize);
        model.addAttribute("questions",list);

        Page<QuestionDto> page =  PageHelper.startPage(pageNo,pageSize);
        List<QuestionInfo> info = qusertionInfoMapper.findByUserId(user.getId());
        PageInfo<QuestionInfo> pageInfo = new PageInfo<>(info,4);
        model.addAttribute("pageInfo",pageInfo);

        return "profile";
    }
}
