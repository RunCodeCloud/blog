package com.mystudy.blog.error;

import com.mystudy.blog.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handle(HttpServletRequest request, Throwable ex, Model model){
        HttpStatus status = getStatus(request);

        if(ex instanceof NullPointerException||404==status.value()){
            model.addAttribute("message","访问资源不存在或已删除");
        }else {
            model.addAttribute("message","服务器冒烟了,稍后再试试吧");
        }

        System.out.println(1111);


        return new ModelAndView("error");
    }
    private  HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("ajax.servlet.error.status_code");
        if(statusCode==null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
