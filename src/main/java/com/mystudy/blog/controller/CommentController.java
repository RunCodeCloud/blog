package com.mystudy.blog.controller;

import com.mystudy.blog.bean.Comment;
import com.mystudy.blog.bean.Message;
import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.CommentDto;
import com.mystudy.blog.exception.ErrorEnum;
import com.mystudy.blog.exception.MyException;
import com.mystudy.blog.mapper.CommentMapper;
import com.mystudy.blog.mapper.MessageMapper;
import com.mystudy.blog.mapper.QusertionInfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
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

    @Resource
    MessageMapper messageMapper;

    @RequestMapping(value = "/comment",method = {RequestMethod.POST})
    @ResponseBody
    @Transactional
    public Object comment(CommentDto commentDto,
                          HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        Map<String,Object> map = new HashMap<>();

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
        BeanUtils.copyProperties(commentDto,comment);

        comment.setCommentator(user.getId());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modefied(System.currentTimeMillis());
        comment.setLike_count(0);
        comment.setDislike_count(0);
        comment.setComment_count(0);

        commentMapper.insert(comment);


        //message信息
        Message message = new Message();
        message.setContent(comment.getContent());
        message.setZuile(user.getId());
        message.setGmt_create(System.currentTimeMillis());
        message.setStatus(0);

        //回复问题
        if("1".equals(type)){
            QuestionInfo info = qusertionInfoMapper.findById(comment.getQuestion_id());
            qusertionInfoMapper.updateComment(info);

            map.put("status","success");
            String url = "http://localhost:80/question/"+info.getId();
            map.put("url",url);

            //message
            message.setOperation(1);
            message.setReply_id(info.getId());
            message.setOriginator_id(info.getCreator());

            messageMapper.insert(message);

            return map;

        }else if("2".equals(type)){

            Comment c = commentMapper.selectById(comment.getQuestion_id());
            commentMapper.updateComment(c);
            //回复评论
            map.put("status","success");
            map.put("commentCount",c.getComment_count()+1);

            //二级评论内容
            CommentDto c2 = new CommentDto();
            BeanUtils.copyProperties(comment,c2);
            c2.setFail(user);

            map.put("img",c2.getFail().getImg_url());
            map.put("name",c2.getFail().getName());
            map.put("content",c2.getContent());

            //message
            //回复评论
            message.setOperation(2);
            message.setReply_id(c.getId());
            message.setOriginator_id(c.getCommentator());

            messageMapper.insert(message);

            return map;
        }else {
            map.put("status","fail");
            map.put("message","评论失败");
            return map;
        }
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
