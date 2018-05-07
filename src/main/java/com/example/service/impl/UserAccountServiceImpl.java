package com.example.service.impl;

import com.example.dataobject.UserAccount;
import com.example.dataobject.UserDetail;
import com.example.enums.UserEnum;
import com.example.exception.UserException;
import com.example.repository.DetailRepository;
import com.example.repository.UserAccountRepository;
import com.example.service.UserAccountService;
import com.example.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/4/4.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository repository;

    @Autowired
    private DetailRepository detailRepository;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public UserAccount findOne(String accountId) {

        return repository.findOne(accountId);
    }

    @Override
    public List<UserAccount> findByAccountIdIn(List<String> accountIdList) {
        return repository.findByAccountIdIn(accountIdList);
    }

    //注册
    @Override
    public UserAccount register(UserAccount user) {

        List<UserAccount> userTest = repository.findByUserEmail(user.getUserEmail());

        if (userTest != null && !userTest.isEmpty()) {
            throw new UserException(UserEnum.EMAIL_IS_EXIST);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        String activityCode = KeyUtil.genUniqueKey();
        message.setFrom(sender);
        message.setTo(user.getUserEmail());

        message.setSubject("用户激活");
        message.setText("请激活赏金猎人账号" + "http://127.0.0.1:8080/user/activate?activeCode=" + activityCode);

        String accountId = KeyUtil.getUserKey();
        user.setActiveCode(activityCode);
        user.setAccountId(accountId);
        user.setState(0);
        user.setDetailId(accountId);

        UserDetail userDetail = new UserDetail();
        userDetail.setDetailId(accountId);

        javaMailSender.send(message);
        detailRepository.save(userDetail);
        return repository.save(user);
    }


    // 用户激活
    @Override
    public UserAccount activity(String activeCode) {
        List<UserAccount> userAccountList = repository.findByActiveCode(activeCode);
        UserAccount userAccount;

        if (userAccountList != null && !userAccountList.isEmpty()) {
            userAccount = userAccountList.get(0);
        } else {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }

        if (!userAccount.getActiveCode().equals(activeCode)) {
            throw new UserException(UserEnum.CODE_NOT_TRUE);
        }

        userAccount.setState(1);

        return repository.save(userAccount);
    }

    // 登陆
    @Override
    public UserAccount login(UserAccount user) {
        List<UserAccount> userAccountList = repository.findByUserEmailAndUserPassword(user.getUserEmail(), user.getUserPassword());

        if (userAccountList == null || userAccountList.isEmpty()) {
            return null;
        } else {
            return userAccountList.get(0);
        }
    }

    // 更新详细用户信息
    @Override
    public UserAccount updateInfo(UserAccount user, UserDetail detail) {
        UserAccount testUser = repository.findOne(user.getAccountId());
        if (testUser == null) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }

        UserAccount userAccount = repository.save(user);
        UserDetail userDetail = detailRepository.save(detail);


        return null;
    }

}
