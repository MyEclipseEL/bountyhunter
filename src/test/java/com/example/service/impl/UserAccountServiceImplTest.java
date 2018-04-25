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
        user.setUserName("打蜡");
        user.setAccountId("111");
        user.setUserEmail("1342746626@qq.com");
        user.setUserPassword("abd1111");
        user.setState(0);
        user.setDetailId("如何");
        user.setActiveCode(KeyUtil.genUniqueKey());

        UserAccount result = userAccountService.register(user);

        Assert.assertNotNull(result);

    }

}