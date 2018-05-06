package com.example.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;


/**
 * Created by 白 on 2018/4/26.
 */
public class UserForm {


    /** 用户名*/
    @NotEmpty(message = "用户名必填")
    private String name;

    /** 登陆密码*/
    @Size(min=6,max=12,message="密码的长度应该在6和12之间")
    @NotEmpty(message = "登陆密码必填")
    private String password;

    /** 用户邮箱*/
    @NotEmpty(message = "邮箱必填")
    private String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
