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
public class UserDetail {

    /** id*/
    @Id
    private String detailId;

    /** 用户手机号*/
    private String userPhone;

    /** 用户地址*/
    private String userAddress;

    /** 用户头像*/
    private String userIcon;

    /** 用户生日*/
    private String userBirthday;

    /** 更新时间*/
    private Date updateTime;
}
