package com.example.util;

import java.util.Random;

/**
 * Created by Administrator on 2018/4/7.
 */
public class KeyUtil {

    /**
     * 生成唯一主键
     *
     * 规则 时间 + 随机数
     * @Return
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }

}
