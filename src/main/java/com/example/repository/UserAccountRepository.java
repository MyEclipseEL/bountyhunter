package com.example.repository;

import com.example.dataobject.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */
public interface UserAccountRepository extends JpaRepository<UserAccount,String> {

    List<UserAccount> findByAccountIdIn(List<String> accountIdList);

    List<UserAccount> findByUserEmailAndUserPassword(String email, String password);

}
