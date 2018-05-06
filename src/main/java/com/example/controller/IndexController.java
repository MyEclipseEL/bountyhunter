package com.example.controller;

import com.example.VO.AssignmentInfoVO;
import com.example.VO.AssignmentVO;
import com.example.converter.AssignmentInfoList2VOlistConverter;
import com.example.dataobject.AssignmentCategory;
import com.example.dataobject.AssignmentInfo;
import com.example.service.AssignmentCategoryService;
import com.example.service.AssignmentService;
import com.example.service.DetailService;
import com.example.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/5/2.
 */
@Controller
@Slf4j
public class IndexController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentCategoryService categoryService;

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private DetailService detailService;



    @RequestMapping({"/","/index"})
    public String index(Model model){
        AssignmentInfoList2VOlistConverter converter = new AssignmentInfoList2VOlistConverter();
        List<AssignmentInfo> assignmentInfoList = assignmentService.findOrderByReward();

        List<AssignmentCategory> categoryList = categoryService.findAll();
        List<AssignmentInfoVO> resultVOList = converter.converter(assignmentInfoList,accountService,detailService);
        log.info("【index】resultVOList={}",resultVOList);
        log.info(String.valueOf(resultVOList.size()));
//        List<AssignmentVO> resultVOList = new ArrayList<>();
        model.addAttribute("category",categoryList);
        model.addAttribute("results",resultVOList);
        return "index";
    }

    @RequestMapping("/login")
    public String toLogin(Model model){
        List<AssignmentCategory> categoryList = categoryService.findAll();
        model.addAttribute("category",categoryList);
        return "login";
    }

    @RequestMapping("/register-01")
    public String toRegister(Model model){
        List<AssignmentCategory> categoryList = categoryService.findAll();
        model.addAttribute("category",categoryList);
        return "register-01";
    }
}
