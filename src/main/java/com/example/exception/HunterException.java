package com.example.exception;

import com.example.enums.ResultEnum;

/**
 * Created by Administrator on 2018/4/7.
 */
public class HunterException extends RuntimeException {

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public HunterException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code =resultEnum.getCode();
    }

    public HunterException(Integer code,String msg){
        super(msg);
        this.code = code;
    }
}
