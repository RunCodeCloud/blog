package com.mystudy.blog.controller;

import com.mystudy.blog.bean.Comment;
import com.mystudy.blog.bean.Message;
import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.CommentDto;
import com.mystudy.blog.dto.QuestionDto;
import com.mystudy.blog.mapper.CommentMapper;
import com.mystudy.blog.mapper.MessageMapper;
import com.mystudy.blog.mapper.QusertionInfoMapper;
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
    QusertionInfoMapper qusertionInfoMapper;
    @Resource
    CommentMapper commentMapper;
    @Resource
    MessageMapper messageMapper;

    @RequestMapping(value = "/question/{id}",method = RequestMethod.GET)
    public String question(@PathVariable(name = "id")Integer id,
                           Model model,
                           HttpServletRequest request,
                           @RequestParam(name = "message",defaultValue = "-1")Integer message){

        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            request.getSession().setAttribute("login","false");
            return "forward:/hello";
        }

        QuestionDto questionDto = service.findById(id);

        //找到问题对应的user，注入model
        User u = userMapper.findById(questionDto.getCreator());
        questionDto.setUser(u);

        //累加评论
        service.updateView(questionDto);
        model.addAttribute("question",questionDto);

        //还有一个问题，就是多级回复的问题
        //同样的解决方式，首先加载一级回复，点击回复框框后应该jia加载的
        // 是根据这个回复的id加载出它回复的内容

        //不应该点击框框后加载，应该把所有的数据打包交给前端，减少交互次数
        List<Comment> comments = commentMapper.findCommentByQuestionInfoId(questionDto.getId());
        List<CommentDto> list = convertFromComment(comments);

        Integer tagId = questionDto.getId();
        List<QuestionInfo> tag = qusertionInfoMapper.findByTag(tagId,questionDto.getTag());

        model.addAttribute("tag",tag);
        model.addAttribute("comments",list);

        //更改message的status
        if(message!=-1){
           messageMapper.updateStatus(1,message);
        }

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

            //查找出所有type=2且question_id为该comment的二级评论
            List<Comment> commentList = commentMapper.selectSecondLevel(commentDto.getId());
            //查找二级评论
            List<CommentDto> dtos = new ArrayList<>();

            if(commentList!=null){

                for(Comment comment1:commentList){
                    User user1 = userMapper.findById(comment1.getCommentator());
                    CommentDto commentDto1 = new CommentDto();
                    BeanUtils.copyProperties(comment1,commentDto1);
                    commentDto1.setFail(user1);
                    dtos.add(commentDto1);
                }
            }

            commentDto.setList(dtos);
            dtoList.add(commentDto);
        }

        return dtoList;
    }
}
