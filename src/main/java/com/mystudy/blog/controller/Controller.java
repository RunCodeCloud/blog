package com.mystudy.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping(value = "/hello",method = {RequestMethod.GET})
    public String hello(){
        return "index";
    }
}
