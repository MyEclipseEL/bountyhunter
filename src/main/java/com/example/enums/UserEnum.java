package com.example.enums;

/**
 * Created by 白 on 2018/4/24.
 */
public enum UserEnum {
    EMAIL_NOT_CONFIRM(10, "邮箱未激活"),

    LOGIN_FAIL(11, "用户名或密码错误"),

    USER_NOT_EXIST(12, "用户不存在"),

    CODE_NOT_TRUE(13, "激活码不正确"),

    ;
    private Integer code;

    private String message;

    UserEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
