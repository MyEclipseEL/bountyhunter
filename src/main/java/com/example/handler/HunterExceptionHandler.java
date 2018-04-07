package com.example.handler;

import com.example.VO.ResultVO;
import com.example.exception.HunterException;
import com.example.util.ResultVOUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/4/7.
 */
@ControllerAdvice
public class HunterExceptionHandler {

    @ExceptionHandler(HunterException.class)
    @ResponseBody
    public ResultVO handlerHunterException(HunterException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }
}
