package com.example.controller;

import com.example.dataobject.AssignmentInfo;
import com.example.dataobject.UserAccount;
import com.example.form.LoginForm;
import com.example.form.UserForm;
import com.example.repository.AssignmentRepository;
import com.example.repository.UserAccountRepository;
import com.example.service.AssignmentService;
import com.example.service.MasterService;
import com.example.service.UserAccountService;
import jdk.nashorn.internal.ir.Assignment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/12.
 */
@RestController
@Slf4j
@RequestMapping("/m")
public class MasterController {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    AssignmentRepository assignmentRepository;

    @GetMapping("go")
    public ModelAndView go() {

        return new ModelAndView("/master/login");
    }

    @PostMapping ("login")
    public ModelAndView login(@Valid LoginForm loginForm,
                              BindingResult bindingResult,
                              Map<String,Object> map) {

        if (loginForm.getEmail().equals("123@qq.com") && loginForm.getPassword().equals("666666")) {
            List<UserAccount> userAccountList = userAccountRepository.findAll();
            List<AssignmentInfo> assignments = assignmentRepository.findAll();


            map.put("userList", userAccountList);
            map.put("asList", assignments);
            return new ModelAndView("/master/index");
        }

        return new ModelAndView("/master/login");
    }
}
