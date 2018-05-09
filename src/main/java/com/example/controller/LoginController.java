package com.example.controller;

import com.example.VO.ResultVO;
import com.example.async.EmailAsync;
import com.example.converter.UserForm2UserAccountConverter;
import com.example.converter.UserInfo2DetailConverter;
import com.example.dataobject.UserAccount;
import com.example.dataobject.UserDetail;
import com.example.enums.UserEnum;
import com.example.exception.UserException;
import com.example.form.LoginForm;
import com.example.form.UserForm;
import com.example.form.UserInfoForm;
import com.example.repository.DetailRepository;
import com.example.service.DetailService;
import com.example.service.UserAccountService;
import com.example.service.impl.DetailServiceImpl;
import com.example.util.ImageUtil;
import com.example.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2018/4/14.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class LoginController {

    @Autowired
    private UserAccountService userService;

    @Autowired
    private EmailAsync emailAsync;

    /*@Autowired
    private DetailService detailService;*/

    @Autowired
    private DetailRepository detailRepository;

    /**
     * 在配置文件中配置的图片保存路径
     */
    @Value("${img.location}")
    private String location;

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
                                                  BindingResult bindingResult)
            throws InterruptedException {

        if (bindingResult.hasErrors()) {
            log.error("[注册用户]参数不正确,userForm={}", userForm);
            throw new UserException(UserEnum.PARAM_ERROR.getCode()
                    , bindingResult.getFieldError().getDefaultMessage());
        }

        UserAccount userAccount = UserForm2UserAccountConverter.converter(userForm);

        /*UserAccount registerResult = userService.register(userAccount);*/

        Future<String> future = emailAsync.task1(userAccount);

        Map<String, String> map = new HashMap<>();
        map.put("show", "若三分钟后未收到邮件请重新注册");

        return ResultVOUtil.success(map);
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

            userService.updateInfo(userAccount, detail);

        } else {
            log.error("[用户信息填写]session中无信息,object={}", object);

        }

/*
        UserDetail detail = UserInfo2DetailConverter.converter(userInfoForm);
*/

        return null;
    }


    @PostMapping("/uploadFile")
    public ResultVO<Map<String, String>> uploadFile(@RequestParam("photo") MultipartFile multipartFile,
                                                    HttpSession session) {

        Object object = session.getAttribute("userAccount");
        UserAccount user;
        if (object != null) {
            user = (UserAccount) object;
        } else {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }


        if (multipartFile.isEmpty() || multipartFile.getOriginalFilename()==null||
                multipartFile.getOriginalFilename().length()<=0) {
            throw new UserException(UserEnum.IMG_NOT_EMPTY);
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            throw new UserException(UserEnum.IMG_FORMAT_ERROR);
        }
        String root_fileName = multipartFile.getOriginalFilename();
        log.info("上传图片:name={},type={}", root_fileName, contentType);
        //处理图片
        //User currentUser = userService.getCurrentUser();
        //获取路径
        String return_path = ImageUtil.getFilePath(user);
        String filePath = location + return_path;
        log.info("图片保存路径={}", filePath);
        String file_name = null;
        try {
            file_name = ImageUtil.saveImg(multipartFile, filePath);
            /*MarkDVo markDVo = new MarkDVo();
            markDVo.setSuccess(0);
            if(StringUtils.isNotBlank(file_name)){
                markDVo.setSuccess(1);
                markDVo.setMessage("上传成功");
                markDVo.setUrl(return_path+ File.separator+file_name);
                markDVo.setCallback(callback);
            }*/

            if (StringUtils.isNotBlank(file_name)) {
                UserDetail detail = detailRepository.findOne(user.getDetailId());
                detail.setUserIcon(return_path + File.separator + file_name);
                detailRepository.save(detail);

                Map<String, String> map = new HashMap<>();
                map.put("img", detail.getUserIcon());

                return ResultVOUtil.success(map);
            }
            return null;

        } catch (IOException e) {
            throw new UserException(UserEnum.SAVE_IMG_ERROE);
        }
    }

   /* @GetMapping("/test")
    public void test() {

        System.out.println("进入测试");
        UserAccount userAccount = new UserAccount();
    }*/
}

