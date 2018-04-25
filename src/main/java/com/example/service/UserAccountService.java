package com.example.service;

import com.example.dataobject.UserAccount;
import com.example.dataobject.UserDetail;
import org.apache.catalina.User;

import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */
public interface UserAccountService {

    UserAccount findOne(String accountId);

    List<UserAccount> findByAccountIdIn(List<String> accountIdList);

    // 注册
    UserAccount register(UserAccount user);

    // 激活
    UserAccount activity(UserAccount user);


}
