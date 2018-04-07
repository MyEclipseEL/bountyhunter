package com.example.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2018/4/7.
 */
@Getter
public enum PayStatus implements CodeEnum{

    WAIT(0,"待支付"),
    SUCCESS(1,"已支付")

    ;

    private Integer code;

    private String message;

    PayStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
