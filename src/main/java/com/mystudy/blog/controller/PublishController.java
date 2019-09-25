package com.mystudy.blog.controller;

import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.mapper.QusertionInfoMapper;
import com.mystudy.blog.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Resource
    UserMapper userMapper;
    @Resource
    QusertionInfoMapper qusertionInfoMapper;

    @RequestMapping(value = "/publish",method = {RequestMethod.GET})
    public String publish(){
        return "publish";
    }
    @RequestMapping(value = "/publish",method = {RequestMethod.POST})
    public String publish(@Param(value = "title")String title,
                          @Param(value = "description")String description,
                          @Param(value = "tag")String tag,
                          HttpServletRequest request,
                          Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title==null||"".equals(title)){
            model.addAttribute("error","问题标题不能为空");
            return "publish";
        }
        if(description==null||"".equals(description)){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag==null||"".equals(tag)){
            model.addAttribute("error","问题标签不能为空");
            return "publish";
        }

        User user = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies){
            if("token".equals(cookie.getName())){
                user = userMapper.findByToken(cookie.getValue());
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        if(user!=null){
            QuestionInfo questionInfo = new QuestionInfo();
            questionInfo.setTitle(title);
            questionInfo.setDescription(description);
            questionInfo.setGmt_create(System.currentTimeMillis());
            questionInfo.setGmt_modified(questionInfo.getGmt_create());
            questionInfo.setCreator(user.getId());
            questionInfo.setTag(tag);

            qusertionInfoMapper.insertQuestionInfo(questionInfo);
        }else {
            model.addAttribute("error","请先登录");
            //request.getSession().setAttribute("error","请先登录");
            return "publish";
        }
        return "redirect:/hello";
    }
}
