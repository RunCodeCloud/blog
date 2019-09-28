package com.mystudy.blog.controller;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Map;

@Controller
@RequestMapping("/error")
public class MyErrorController implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("error");

        if(status.is4xxClientError()){
            mv.addObject("message","访问资源不存在或已删除");
        }
        else if(status.is5xxServerError()){
            mv.addObject("message","服务器冒烟了,稍后再试试吧");
        }else {
            mv.addObject("message","未知错误发生");
        }
        return mv;
    }

   /* public String getErrorPath() {
        return "error";
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request,
                                  Model model){

        HttpStatus status = getStatus(request);
        if(status.is4xxClientError()){
            model.addAttribute("message","访问资源不存在或已删除");
        }
        else if(status.is5xxServerError()){

            model.addAttribute("message","服务器冒烟了,稍后再试试吧");
        }else {
            model.addAttribute("message","未知错误发生");
        }

        String s1  = status.getReasonPhrase();
        String s = status.name();
        System.out.println(s);
        System.out.println(s1);
        System.out.println(status.value());
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("ajax.servlet.error.status_code");
        if(statusCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }*/
}
