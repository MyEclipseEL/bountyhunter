package com.example.exception;

import com.example.enums.UserEnum;

/**
 *  用户异常
 *
 * Created by 白 on 2018/4/24.
 */
public class UserException extends RuntimeException {

    private Integer code;

    public UserException(UserEnum userEnum) {
        super(userEnum.getMessage());
        this.code = userEnum.getCode();
    }

    public UserException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
