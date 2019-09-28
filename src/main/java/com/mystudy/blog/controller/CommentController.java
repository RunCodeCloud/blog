package com.mystudy.blog.controller;

import com.mystudy.blog.bean.Comment;
import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.CommentDto;
import com.mystudy.blog.exception.ErrorEnum;
import com.mystudy.blog.exception.MyException;
import com.mystudy.blog.mapper.CommentMapper;
import com.mystudy.blog.mapper.QusertionInfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController{

    @Resource
    QusertionInfoMapper qusertionInfoMapper;

    @Resource
    CommentMapper commentMapper;

    //一级评论
    @RequestMapping(value = "/comment",method = {RequestMethod.POST})
    @ResponseBody
    @Transactional
    public Object comment(CommentDto commentDto,
                          HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        Map<String,String> map = new HashMap<>();

        if(user==null){
            map.put("status","fail");
            map.put("message","用户未登录");
            return map;
        }

        Integer  parent_id = commentDto.getParent_id();
        String content = commentDto.getContent();
        String type = commentDto.getType();

        if(parent_id==null||parent_id==0){
            map.put("status","fail");
            map.put("message","评论内容不存在，或已删除");
           return map;
        }
        if(content==null||"".equals(content)){
            map.put("status","fail");
            map.put("message","评论内容不能为空");
            return map;
        }
        if(type==null||"".equals(type)){
            map.put("status","fail");
            map.put("message","评论失败");
            return map;
        }

        Comment comment = new Comment();
        comment.setCommentator(user.getId());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modefied(System.currentTimeMillis());
        comment.setLike_count(0);
        comment.setDislike_count(0);
        comment.setComment_count(0);
        BeanUtils.copyProperties(commentDto,comment);
        commentMapper.insert(comment);

        QuestionInfo info = qusertionInfoMapper.findById(comment.getQuestion_id());
        qusertionInfoMapper.updateComment(info);

        //回复评论
        //回复问题
        map.put("status","success");
        String url = "http://localhost:8080/question/"+info.getId();
        map.put("url",url);
        return map;
    }

    @RequestMapping(value = "/comment/secondLevel",method = {RequestMethod.POST})
    @ResponseBody
    public Object comment(@RequestParam(name = "commentId",defaultValue = "")Integer id,
                          @RequestParam(name = "like",defaultValue = "0")Integer like,
                          @RequestParam(name = "dislike",defaultValue = "0")Integer dislike,
                          HttpServletRequest request){

        Map<String,Object> map = new HashMap<>();

        Comment comment = commentMapper.selectById(id);

        if(like!=null&&like!=0){
            if(like>0){
                commentMapper.updateLike(comment);
            }else {
                commentMapper.updateLikeFun(comment);
            }
            Integer newLike = commentMapper.selectLike(id);
            map.put("status","success");
            map.put("like",newLike);
        }else if(dislike!=null&&dislike!=0){
            if(dislike>0){
                commentMapper.updateDislike(comment);
            }else {
                commentMapper.updateDislikeFun(comment);
            }
            Integer newDisLike = commentMapper.selectDisLike(id);
            map.put("status","success");
            map.put("dislike",newDisLike);
        }else {
            map.put("status","fail");
            map.put("message","操作失败");
            return map;
        }
        return map;
    }
}
