package com.example.async;

import com.example.dataobject.UserAccount;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by 白 on 2018/5/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailAsyncTest {

    @Autowired
    private EmailAsync emailAsync;
    @Test
    public void task1() throws Exception {
        UserAccount user = new UserAccount();
        user.setAccountId("12222");
        user.setUserName("ccccc");
        user.setUserEmail("2241852868@qq.com");
        user.setUserPassword("dsdsds");

        Future<String> future = emailAsync.task1(user);
        while (true) {
            if (future.isDone()) {
                System.out.println("执行完毕" + future.get());
                break;
            }
        }
    }

}