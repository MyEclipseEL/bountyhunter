package com.example.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2018/5/10.
 */
@Getter
public enum ReceiveStatus implements CodeEnum {

    NEW(0,"新任务"),
    RECEIVED(1,"被接取"),
    CANCELED(-1,"已取消"),
    FINISHED(2,"已完结"),

    ;

    private Integer code;

    private String message;

    ReceiveStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
