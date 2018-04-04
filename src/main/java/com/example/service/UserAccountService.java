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

}
