package com.example.VO;

import com.example.dataobject.UserAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/3.
 */
@Data
public class AssignmentInfoVO {

    /**
     * id
     */
    @JsonProperty("id")
    private String assignmentId;

    /** 任务名称*/
    @JsonProperty("name")
    private String assignmentName;

    /**
     * 任务发布者
     */
    @JsonProperty("owner")
    private UserAccountVO assignmentOwner;

    /** 任务类别*/
    @JsonProperty("type")
    private Integer categoryType;

    /** 任务回报*/
    @JsonProperty("reward")
    private BigDecimal assignmentReward;

    /** 任务描述*/
    @JsonProperty("description")
    private String assignmentDescription;

    /** 任务图片*/
    @JsonProperty("icon")
    private String assignmentIcon;

    /** 任务发布位置*/
    @JsonProperty("position")
    private String assignmentPosition;

    @JsonProperty("receiver")
    private UserAccountVO receiver;

    @JsonProperty("phone")
    private String userPhone;
    /** 任务发布时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;


}
