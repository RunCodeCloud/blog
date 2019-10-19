//package com.mystudy.blog.error;
//
//import com.alibaba.fastjson.JSON;
//import com.mystudy.blog.exception.ErrorEnum;
//import com.mystudy.blog.exception.MyException;
//import org.springframework.http.HttpStatus;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestControllerAdvice
//public class ErrorHandler {
//
//    @ExceptionHandler(Exception.class)
//    public Object handle(HttpServletRequest request, Throwable ex, Model model){
//
//        HttpStatus status = getStatus(request);
//        String contentType =  request.getContentType();
//
//        if("application/json".equals(contentType)){
//            System.out.println(ex.getMessage());
//            Map<String,Object> map = new HashMap<>();
//            if(ErrorEnum.USER_NOT_LOGIN_T.getMessage().equals(ex.getMessage())){
//                map.put("status","fail");
//                map.put("message","用户未登录");
//            }else if(ErrorEnum.COMMENT_NO_EXIST.getMessage().equals(ex.getMessage())){
//                map.put("status","fail");
//                map.put("message","访问资源不存在或已删除");
//            }else{
//                map.put("status","fail");
//                map.put("message","未知错误");
//            }
//            JSON json = (JSON)JSON.toJSON(map);
//            return json;
//        }else {
//            if(ex instanceof NullPointerException||404==status.value()){
//                model.addAttribute("message","访问资源不存在或已删除");
//            }else {
//                model.addAttribute("message","服务器冒烟了,稍后再试试吧");
//            }
//            System.out.println("mv");
//            return new ModelAndView("error");
//        }
//    }
//    private  HttpStatus getStatus(HttpServletRequest request){
//        Integer statusCode = (Integer) request.getAttribute("ajax.servlet.error.status_code");
//        if(statusCode==null){
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//        return HttpStatus.valueOf(statusCode);
//    }
//}
