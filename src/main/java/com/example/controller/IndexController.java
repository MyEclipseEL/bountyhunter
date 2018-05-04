package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2018/5/2.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "forward:/index.html";
    }
}
