package com.mystudy.blog.controller;

import com.mystudy.blog.bean.User;
import com.mystudy.blog.dto.AccessTokenDTO;
import com.mystudy.blog.dto.GitHubUser;
import com.mystudy.blog.mapper.UserMapper;
import com.mystudy.blog.provider.GitHubProvider;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    GitHubProvider gitHubProvider;
    @Resource
    UserMapper userMapper;

    @Value(value = "${github.client.id}")
    private String clientId;
    @Value(value = "${github.client.secret}")
    private String clientSecret;
    @Value(value = "${github.redirect.uri}")
    private String redirectUri;


    @RequestMapping(value = "/callback",method = {RequestMethod.GET})
    public String callback(@Param(value = "code")String code,
                           @Param(value = "state")String state) throws IOException {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);

        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        System.out.println(gitHubUser.getId());


        if(gitHubUser!=null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitHubUser.getName());
            user.setAccountId(gitHubUser.getId().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGetModified(user.getGmtCreate());

            userMapper.insertUser(user);
            request.getSession().setAttribute("user",gitHubUser);
            return "redirect:/hello";
        }else {
            return "redirect:/hello";
        }
    }
}
