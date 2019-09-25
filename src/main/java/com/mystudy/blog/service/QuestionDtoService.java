package com.mystudy.blog.service;

import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.QuestionDto;
import com.mystudy.blog.mapper.QusertionInfoMapper;
import com.mystudy.blog.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionDtoService {

    @Resource
    QusertionInfoMapper qusertionInfoMapper;

    @Resource
    UserMapper userMapper;

    public List<QuestionDto>  findAll(){

        List<QuestionInfo> list =  qusertionInfoMapper.findAll();
        return convertFromInfo(list);
    }

    public List<QuestionDto> convertFromInfo(List<QuestionInfo> list){
        if(list==null){
            return null;
        }
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for(QuestionInfo info:list){
            QuestionDto questionDto = new QuestionDto();
            User user = userMapper.findById(info.getCreator());
            BeanUtils.copyProperties(info,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
}
