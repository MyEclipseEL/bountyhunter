package com.example.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/2.
 */
@Entity
@DynamicUpdate
public class UserAccount {

    /** 账户*/
    @Id
    private String accountId;

    /** 用户名*/
    private String userName;

    /** 登陆密码*/
    private String userPassword;

    /** 用户具体信息*/
    private String detailId;

    /** 用户邮箱*/
    private String userEmail;

    /** 激活码 **/
    private String activeCode;

    /** 激活状态  默认为 0 未激活**/
    private int state;

    /** 创建时间 **/
    private Date createTime;


    /** 用户头像 **/
    private String icon = "/usericon/def/tim.jpg";


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "accountId='" + accountId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", detailId='" + detailId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", activeCode='" + activeCode + '\'' +
                ", state=" + state +
                ", createTime=" + createTime +
                ", icon='" + icon + '\'' +
                '}';
    }
}
