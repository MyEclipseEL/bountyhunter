package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/4/14.
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    /**
     * 用户登陆
     * */
    @PostMapping("/login")
    public void login(){

    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public void register() {


    }





}
