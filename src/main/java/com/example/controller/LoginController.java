package com.example.controller;

import com.example.VO.ResultVO;
import com.example.converter.UserForm2UserAccountConverter;
import com.example.dataobject.UserAccount;
import com.example.enums.UserEnum;
import com.example.exception.UserException;
import com.example.form.LoginForm;
import com.example.form.UserForm;
import com.example.service.UserAccountService;
import com.example.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/14.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {

    @Autowired
    private UserAccountService userService;

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    public ResultVO<Map<String, String>> login(@Valid LoginForm loginForm,
                                               BindingResult bindingResult) {

        System.out.println("进入--------------------");

        if (bindingResult.hasErrors()) {
            log.error("[注册用户]参数不正确,loginForm={}", loginForm);
            throw new UserException(UserEnum.PARAM_ERROR.getCode()
                    , bindingResult.getFieldError().getDefaultMessage());
        }

        UserAccount userAccount = new UserAccount();
        userAccount.setUserEmail(loginForm.getEmail());
        userAccount.setUserPassword(loginForm.getPassword());


        System.out.println("登陆---------------------");


        return null;

    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResultVO<Map<String, String>> register(@Valid UserForm userForm,
                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("[注册用户]参数不正确,userForm={}", userForm);
            throw new UserException(UserEnum.PARAM_ERROR.getCode()
                    , bindingResult.getFieldError().getDefaultMessage());
        }

        UserAccount userAccount = UserForm2UserAccountConverter.converter(userForm);

        UserAccount registerResult = userService.register(userAccount);

        Map<String, String> map = new HashMap<>();
        map.put("userid", registerResult.getAccountId());

        return ResultVOUtil.success(map);

    }


    @PostMapping("/test")
    public void test(@RequestParam("test") String test) {

        System.out.println("进入测试");


    }

    @GetMapping("/test")
    public void test2() {
        System.out.println("进入测试sssssss");

    }

}
