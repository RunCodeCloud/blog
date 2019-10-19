package com.mystudy.blog.controller;

import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.exception.ErrorEnum;
import com.mystudy.blog.exception.MyException;
import com.mystudy.blog.mapper.QusertionInfoMapper;
import com.mystudy.blog.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Resource
    UserMapper userMapper;
    @Resource
    QusertionInfoMapper qusertionInfoMapper;

    @RequestMapping(value = "/publish/{id}",method = {RequestMethod.GET})
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model){

        QuestionInfo info =  qusertionInfoMapper.findById(id);
        String title = info.getTitle();
        String description = info.getDescription();
        String tag = info.getTag();

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("id",id);

        return "publish";
    }

    @RequestMapping(value = "/publish",method = {RequestMethod.GET})
    public String publish(){
        return "publish";
    }
    @RequestMapping(value = "/publish",method = {RequestMethod.POST})
    public String publish(@Param(value = "title")String title,
                          @Param(value = "description")String description,
                          @Param(value = "tag")String tag,
                          @RequestParam(value = "id",required = false)Integer id,
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

        User user = (User) request.getSession().getAttribute("user");

        if(user!=null){

            QuestionInfo questionInfo = new QuestionInfo();
            if(id==null||id==0){
                questionInfo.setTitle(title);
                questionInfo.setDescription(description);
                questionInfo.setGmt_create(System.currentTimeMillis());
                questionInfo.setGmt_modified(questionInfo.getGmt_create());
                questionInfo.setCreator(user.getId());
                questionInfo.setTag(tag);

                questionInfo.setComment_count(0);
                questionInfo.setView_count(0);
                questionInfo.setLike_count(0);

                qusertionInfoMapper.insertQuestionInfo(questionInfo);
            }else {
                qusertionInfoMapper.updateById(title,description,tag,id);
            }

        }else {
            model.addAttribute("error","请先登录");
            return "publish";
        }
        return "redirect:/";
    }
}
