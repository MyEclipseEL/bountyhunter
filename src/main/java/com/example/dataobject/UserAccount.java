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
@Data
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

    /** 创建时间*/
    private Date createTime;

    /** 更新时间*/
    private Date updateTime;

}
