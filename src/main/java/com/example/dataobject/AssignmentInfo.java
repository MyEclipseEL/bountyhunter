package com.example.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/1.
 */
@Entity
@Data
public class AssignmentInfo {

    /** 任务id*/
    @Id
    private String assignmentId;

    /** 任务发布者*/
    private String assignmentOwner;

    /** 任务接取者*/
    private String assignmentReceive;

    /** 任务类别*/
    private Integer categoryType;

    /** 任务回报 金额*/
    private BigDecimal assignmentReward;

    /** 任务描述*/
    private String assignmentDescription;

    /** 任务图片*/
    private String assignmentIcon;

    /** 任务状态*/
    private Integer assignmentStatus;

    /** 支付状态*/
    private Integer payStatus;

    /** 任务发布位置*/
    private String assignmentPosition;

    /** 创建时间*/
    private Date createTime;

    /** 接取时间*/
    private Date receiveTime;


}
