package com.example.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2018/4/7.
 */
@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数错误"),
    ASSIGNMENT_NOT_EXIST(2,"任务不存在"),
    ACCOUNT_NOT_EXIST(3,"用户不存在"),
    OWNER_NOT_EXIST(4,"没有匹配的发布者"),
    ACCOUNT_EMPTY(5,"用户信息为空"),
    RECEIVE_EXIST(6,"任务接取人已存在"),
    ACCOUNT_NOT_MATCHING(7,"用户不匹配"),
    STATUS_NOT_TRUE(8,"任务状态不正确")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
