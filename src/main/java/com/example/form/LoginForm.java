package com.example.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by 白 on 2018/4/27.
 */
public class LoginForm {

    /** 登陆密码*/
    @NotEmpty(message = "登陆密码必填")
    private String password;

    /** 用户邮箱*/
    @NotEmpty(message = "邮箱必填")
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
