package com.example.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/4/7.
 */
@Data
public class AssignmentForm {

    /** 任务id*/
    private String id;

    /** 任务名字*/
    @NotEmpty(message = "任务名称不能为空")
    private String name;


    /** 任务类别*/
//    @NotEmpty(message = "类别必填")
    private Integer type;

    /** 任务回报*/
//    @NotEmpty(message = "不存在学雷锋的，请填写金额")
    private BigDecimal reward;

    /** 任务描述*/
    @NotEmpty(message = "请说明任务需求")
    private String description;

    /** 任务图片*/
    @NotEmpty(message = "请配图")
    private String icon;

    /** 任务发布位置*/
    @NotEmpty(message = "位置为空")
    private String position;


}
