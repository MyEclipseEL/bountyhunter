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


    /**
     * 生成较短的主键
     */

    public static synchronized String getUserKey(){

        String str="QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        int length = str.length();
        Random random = new Random();

        StringBuffer sb = new StringBuffer();
        for (int i=0;i<3;i++) {
            int number = random.nextInt(length);
            sb.append(str.charAt(number));
        }

        return System.currentTimeMillis() + sb.toString();
    }



}
