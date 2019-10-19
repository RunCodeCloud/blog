package com.mystudy.blog.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mystudy.blog.bean.Message;
import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.MessageDto;
import com.mystudy.blog.dto.QuestionDto;
import com.mystudy.blog.mapper.CommentMapper;
import com.mystudy.blog.mapper.MessageMapper;
import com.mystudy.blog.mapper.QusertionInfoMapper;
import com.mystudy.blog.mapper.UserMapper;
import com.mystudy.blog.service.QuestionDtoService;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Resource
    MessageMapper messageMapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    QusertionInfoMapper qusertionInfoMapper;

    @Resource
    QuestionDtoService service;


    @RequestMapping(value = "/profile/{action}",method = RequestMethod.GET)
    public String profile(@PathVariable(value = "action")String action,
                          @RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo,
                          @RequestParam(value = "pageSize",defaultValue="5")Integer pageSize,
                          HttpServletRequest request,
                          Model model){

        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            request.getSession().setAttribute("isLogin","false");
            return "forward:/";
        }

        if("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("secionName","我的提问");


            List<QuestionDto> list = service.findByPage(user.getId(),pageNo,pageSize);
            model.addAttribute("questions",list);

            PageHelper.startPage(pageNo,pageSize);
            List<QuestionInfo> info = qusertionInfoMapper.findByUserId(user.getId());
            PageInfo<QuestionInfo> pageInfo = new PageInfo<>(info,4);
            model.addAttribute("pageInfo",pageInfo);

        }else if("replies".equals(action)){

            model.addAttribute("section","replies");
            model.addAttribute("secionName","最新回复");

            //在这里应该获取跟当前用户有关的message
            //展示
            Integer offset = pageSize * (pageNo - 1);
            List<Message> list1 = messageMapper.findByUserIdAndLimit(user.getId(), offset, pageSize);

            List<MessageDto> dtoList = convertFromBean(list1);

            model.addAttribute("message",dtoList);

            //查询跟自己有关的message，所有status为0的
            int news = messageMapper.findStatus(user.getId()).size();
            model.addAttribute("news",news);
            //分页
            PageHelper.startPage(pageNo,pageSize);
            List<Message> list = messageMapper.selectAllById(user.getId());
            PageInfo<Message> pageInfo = new PageInfo<>(list,4);
            model.addAttribute("pageInfo",pageInfo);
        }
        return "profile";
    }

    private List<MessageDto> convertFromBean(List<Message> list){

        if(list==null){
            return null;
        }

        List<MessageDto> l = new ArrayList<>();

        for(Message message:list){

            MessageDto messageDto = new MessageDto();
            String commentatorName =  userMapper.findById(message.getZuile()).getName();
            String title;
            Integer question_id;

            if (message.getOperation()==1){
                title = qusertionInfoMapper.findById(message.getReply_id()).getTitle();
                question_id = message.getReply_id();
            }else {
                title = commentMapper.selectById(message.getReply_id()).getContent();
                question_id = commentMapper.selectById(message.getReply_id()).getQuestion_id();
            }


            BeanUtils.copyProperties(message,messageDto);

            messageDto.setReply_content(title);
            messageDto.setCommentator_name(commentatorName);
            messageDto.setQuestion_id(question_id);

            l.add(messageDto);
        }

        return l;
    }
}
