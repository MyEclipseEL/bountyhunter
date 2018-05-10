package com.example.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/4/7.
 */
@Data
public class AssignmentForm {

    /** 任务id*/
//    @JsonIgnore
//    private String id;

    /** 任务名字*/
    @NotEmpty(message = "任务名称不能为空")
    private String name;


    /** 任务类别*/
    @NotNull(message = "类别必填")
    private Integer type;

    /** 任务回报*/
    @NotNull(message = "不存在学雷锋的，请填写金额")
    private BigDecimal reward;

    /** 任务描述*/
    @NotEmpty(message = "请说明任务需求")
    private String description;

    @NotNull(message = "求留下电话")
    private String phone;

    /** 任务图片*/
//   @URL(message = "请上传图片")
//    @NotNull(message = "请上传图片")
//    private String icon;

    /** 任务发布位置*/
    @NotEmpty(message = "位置为空")
    private String position;


}
