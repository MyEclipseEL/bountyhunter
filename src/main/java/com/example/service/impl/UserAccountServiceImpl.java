package com.example.service.impl;

import com.example.dataobject.UserAccount;
import com.example.repository.UserAccountRepository;
import com.example.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository repository;

    @Override
    public UserAccount findOne(String accountId) {
        return repository.findOne(accountId);
    }

    @Override
    public List<UserAccount> findByAccountIdIn(List<String> accountIdList) {
        return repository.findByAccountIdIn(accountIdList);
    }
}
