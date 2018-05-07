package com.example.async;

import com.example.dataobject.UserAccount;
import com.example.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 *  异步执行邮箱， 使注册用户不再等待过久
 *
 * Created by 白 on 2018/5/7.
 */

@Component
public class EmailAsync {

    @Autowired
    private UserAccountService userService;

    @Async
    public Future<String> task1(UserAccount userAccount) throws InterruptedException {


        Thread.sleep(100);
        UserAccount registerResult = userService.register(userAccount);
        return new AsyncResult<String>(registerResult.getUserEmail());
    }

    @Async
    public void task2() throws InterruptedException {
        Thread.sleep(100);
        System.out.println("---------------sdsdsdsdsd");
    }


}
