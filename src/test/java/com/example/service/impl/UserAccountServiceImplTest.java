package com.example.service.impl;

import com.example.dataobject.UserAccount;
import com.example.service.UserAccountService;
import com.example.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by 白 on 2018/4/24.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountServiceImplTest {

    @Autowired
    private UserAccountService userAccountService;


    @Test
    public void register() throws Exception {

        UserAccount user = new UserAccount();
        user.setUserName("ccccc");
        user.setUserEmail("2241852868@qq.com");
        user.setUserPassword("dsdsds");

        UserAccount result = userAccountService.register(user);

        Assert.assertNotNull(result);

    }

    @Test
    public void login() throws Exception {

        UserAccount user = new UserAccount();
        user.setUserName("绅士");
        user.setUserEmail("22233@qq.com");
        user.setUserPassword("dsdsd");

        UserAccount result = userAccountService.login(user);

        System.out.println("result" + result);

        Assert.assertNotNull(result);
    }

}