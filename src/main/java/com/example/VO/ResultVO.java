package com.example.VO;

import lombok.Data;

/**
 * Created by Administrator on 2018/4/2.
 */
@Data
public class ResultVO<T> {
    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}
