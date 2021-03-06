package com.example.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by 白 on 2018/5/6.
 */
public class UserInfoForm {

    @NotEmpty(message = "用户名必填")
    private String name;

    @NotEmpty(message = "邮箱必填")
    private String email;

    @NotEmpty(message = "密码")
    private String password;

    private String phone;

    private String address;

    private String birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}
