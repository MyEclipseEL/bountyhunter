package com.example.VO;

import com.example.dataobject.UserDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 回显用户信息使用
 *
 * Created by 白 on 2018/5/10.
 */

public class UserInfoVO {


    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;


    @JsonProperty("password")
    private String password;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("address")
    private String address;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("icon")
    private String icon;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
