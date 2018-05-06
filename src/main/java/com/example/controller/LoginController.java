package com.example.controller;

import com.example.VO.ResultVO;
import com.example.converter.UserForm2UserAccountConverter;
import com.example.converter.UserInfo2DetailConverter;
import com.example.dataobject.UserAccount;
import com.example.dataobject.UserDetail;
import com.example.enums.UserEnum;
import com.example.exception.UserException;
import com.example.form.LoginForm;
import com.example.form.UserForm;
import com.example.form.UserInfoForm;
import com.example.service.UserAccountService;
import com.example.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
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
    public ResultVO<UserAccount> login(@Valid LoginForm loginForm,
                                       BindingResult bindingResult,
                                       HttpServletRequest request) throws ServletException, IOException {

        System.out.println("进入--------------------");

        if (bindingResult.hasErrors()) {
            log.error("[注册用户]参数不正确,loginForm={}", loginForm);
            throw new UserException(UserEnum.PARAM_ERROR.getCode()
                    , bindingResult.getFieldError().getDefaultMessage());
        }

        UserAccount userAccount = new UserAccount();
        userAccount.setUserEmail(loginForm.getEmail());
        userAccount.setUserPassword(loginForm.getPassword());

        UserAccount user = userService.login(userAccount);
        if (user == null || user.getState()!=1) {
            log.error("【用户名或密码错误,或未前往邮箱激活】,user={}", user);
            throw new UserException(UserEnum.ACCOUNT_ERROR);
        } else {
            request.getSession().setAttribute("userAccount", user);
        }

        return ResultVOUtil.success(user);
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


    /**
     * 用户详细信息
     * @param userInfoForm
     * @param bindingResult
     * @param session
     * @return
     */
    @PostMapping("/updateInfo")
    public ResultVO update(@Valid UserInfoForm userInfoForm,
                           BindingResult bindingResult,
                           HttpSession session) {

        if (bindingResult.hasErrors()) {
            log.error("【用户信息填写】参数不正确,userInfoForm={}", userInfoForm);
            throw new UserException(UserEnum.PARAM_ERROR.getCode()
                    , bindingResult.getFieldError().getDefaultMessage());
        }

        Object object = session.getAttribute("userAccount");

        if (object != null) {
            UserAccount userAccount = (UserAccount) object;
            UserDetail detail = UserInfo2DetailConverter.converter(userInfoForm, userAccount.getDetailId());

            userAccount.setUserName(userInfoForm.getName());
            userAccount.setUserEmail(userInfoForm.getEmail());
            userAccount.setUserPassword(userInfoForm.getPassword());


        } else {
            log.error("[用户信息填写]session中无信息,object={}", object);

        }

/*
        UserDetail detail = UserInfo2DetailConverter.converter(userInfoForm);
*/

        return null;
    }

    /**
     *  用户激活
     * @param activeCode
     */
    @GetMapping("/activate")
    public void activate(@RequestParam("activeCode") String activeCode) {
        System.out.println("进入 激活");
        UserAccount userAccount = userService.activity(activeCode);
    }

   /* @GetMapping("/test")
    public void test() {

        System.out.println("进入测试");
        UserAccount userAccount = new UserAccount();

    }*/
}

