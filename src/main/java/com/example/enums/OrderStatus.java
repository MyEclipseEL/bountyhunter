package com.example.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2018/4/7.
 */
@Getter
public enum OrderStatus implements CodeEnum {

    NEW(0,"新接取"),
    FINISH(1,"完结"),
    CANCEL(-1,"已取消"),

    ;

    private Integer code;

    private String message;

    OrderStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
