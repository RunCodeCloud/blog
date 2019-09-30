package com.mystudy.blog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.PageDto;
import com.mystudy.blog.dto.QuestionDto;
import com.mystudy.blog.mapper.QusertionInfoMapper;
import com.mystudy.blog.mapper.UserMapper;
import com.mystudy.blog.service.QuestionDtoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LoginController {

    @Resource
    QuestionDtoService service;

    @Resource
    QusertionInfoMapper qusertionInfoMapper;

    @Resource
    UserMapper userMapper;

    @RequestMapping(value = "/hello",method = {RequestMethod.GET})
    public String hello(@RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo,
                        @RequestParam(value = "pageSize",defaultValue="5")Integer pageSize,
                        HttpServletRequest request,
                        Model model){

        //显示第几页内容  一页几条数据
        List<QuestionDto> list = service.findByPage(pageNo,pageSize);
        model.addAttribute("questions",list);

        Page<QuestionDto> page =  PageHelper.startPage(pageNo,pageSize);
        List<QuestionInfo> infos = qusertionInfoMapper.findAll();
        PageInfo<QuestionInfo> pageInfo = new PageInfo<>(infos,4);
        model.addAttribute("pageInfo",pageInfo);

        List<QuestionInfo> hotQuestion = qusertionInfoMapper.findHostQuestion();
        model.addAttribute("hotQuestion",hotQuestion);

        System.out.println(hotQuestion.size());

        return "index";
    }
}
