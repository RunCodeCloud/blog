package com.mystudy.blog.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mystudy.blog.bean.QuestionInfo;
import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.PageDto;
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

    public List<QuestionDto> findByPage(Integer pageNum, Integer pageSize) {

        Integer offset = pageSize * (pageNum - 1);
        List<QuestionInfo> list1 = qusertionInfoMapper.findByPage(offset, pageSize);
        List<QuestionDto> dtoList = convertFromInfo(list1);

        return dtoList;
        //page.getTotal();总条数
        //page.getPages();总页数
        //page.getPageSize(); 每页条数
        //page.getPageNum(); 当前页
        //page.getPrePage() 上一页
        //page.getNextPage() 下一页
    }

    public List<QuestionDto> findByPageAndReq(String req,Integer pageNum, Integer pageSize) {

        Integer offset = pageSize * (pageNum - 1);
        List<QuestionInfo> list1 = qusertionInfoMapper.findByPageAndReq(req,offset, pageSize);
        List<QuestionDto> dtoList = convertFromInfo(list1);

        return dtoList;
    }

    public List<QuestionDto> findByPage(Integer id, Integer pageNum, Integer pageSize) {

        Integer offset = pageSize * (pageNum - 1);
        List<QuestionInfo> list1 = qusertionInfoMapper.findByUserIdAndLimit(id, offset, pageSize);
        List<QuestionDto> dtoList = convertFromInfo(list1);
        return dtoList;
    }

    public QuestionDto findById(Integer id) {
        QuestionInfo questionInfo = qusertionInfoMapper.findById(id);

        if (questionInfo == null) {
            return null;
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(questionInfo, questionDto);
        return questionDto;
    }


    public List<QuestionDto> findAll() {

        List<QuestionInfo> list = qusertionInfoMapper.findAll();
        return convertFromInfo(list);
    }

    public List<QuestionDto> convertFromInfo(List<QuestionInfo> list) {
        if (list == null) {
            return null;
        }
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (QuestionInfo info : list) {
            QuestionDto questionDto = new QuestionDto();
            User user = userMapper.findById(info.getCreator());
            BeanUtils.copyProperties(info, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }

    public void updateView(QuestionDto questionDto){
        qusertionInfoMapper.updateView(questionDto);
    }

}


