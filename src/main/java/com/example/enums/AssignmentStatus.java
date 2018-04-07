package com.example.enums;

import lombok.Getter;

/**
 * Created by Administrator on 2018/4/7.
 */
@Getter
public enum AssignmentStatus implements CodeEnum {

    NEW(0,"新发布"),
    RECEIVED(1,"被接取"),
    CANCEL(-1,"取消")
    ;
    private Integer code;

    private String message;

    AssignmentStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
