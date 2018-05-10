package com.example.service;

import com.example.dataobject.UserAccount;
import com.example.dataobject.UserDetail;

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
    UserAccount activity(String activeCode);

    //登陆
    UserAccount login(UserAccount user);

    //更新
     void updateInfo(UserAccount user, UserDetail detail);


     void save(UserAccount user);
}
