package com.example.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

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
//    @JsonProperty("type")
    @JsonIgnore
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

    /** */


}
