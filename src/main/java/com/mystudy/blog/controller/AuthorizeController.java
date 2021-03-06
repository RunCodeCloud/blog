package com.mystudy.blog.controller;

import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.AccessTokenDTO;
import com.mystudy.blog.dto.GitHubUser;
import com.mystudy.blog.mapper.UserMapper;
import com.mystudy.blog.provider.GitHubProvider;
import com.mystudy.blog.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    GitHubProvider gitHubProvider;

    @Resource
    UserService service;

    @Resource
    UserMapper userMapper;

    @Value(value = "${github.client.id}")
    private String clientId;
    @Value(value = "${github.client.secret}")
    private String clientSecret;
    //@Value(value = "${github.redirect.uri}")
    //private String redirectUri;

    @Value(value = "${public.ip}")
    private String publicIp;

    @RequestMapping(value = "/callback",method = {RequestMethod.GET})
    public String callback(@Param(value = "code")String code,
                           @Param(value = "state")String state,
                           HttpServletResponse response) throws IOException {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(publicIp);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);

        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        if(gitHubUser!=null){
            User u = service.findUserByAccountId(gitHubUser.getId().toString());
            if(u==null){
                User user = new User();
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setName(gitHubUser.getName());
                user.setAccountId(gitHubUser.getId().toString());
                user.setGmtCreate(System.currentTimeMillis());
                user.setGetModified(user.getGmtCreate());
                user.setImg_url(gitHubUser.getAvatar_url());

                userMapper.insertUser(user);
                response.addCookie(new Cookie("token",token));
            }else {
                response.addCookie(new Cookie("token",u.getToken()));
            }
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
