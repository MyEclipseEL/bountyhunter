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

        UserAccount userTest = repository.findOne(user.getUserEmail());
        if (userTest != null) {
            throw new UserException(UserEnum.EMAIL_IS_EXIST);
        }

        SimpleMailMessage message = new SimpleMailMessage();
        String activityCode = KeyUtil.genUniqueKey();
        message.setFrom(sender);
        message.setTo(user.getUserEmail());

        message.setSubject("用户激活");
        message.setText("请激活赏金猎人账号"+"http://127.0.0.1/bountyhunter?activityCode="+activityCode);

        String accountId = KeyUtil.getUserKey();
        user.setActiveCode(activityCode);
        user.setAccountId(accountId);
        user.setState(0);
        user.setDetailId(activityCode);


        UserDetail userDetail = new UserDetail();
        userDetail.setDetailId(accountId);
        detailRepository.save(userDetail);

        javaMailSender.send(message);

        return repository.save(user);
    }


    // 用户激活
    @Override
    public UserAccount activity(UserAccount user) {

        UserAccount userAccount = repository.findOne(user.getAccountId());
        if (userAccount == null) {
            throw new UserException(UserEnum.USER_NOT_EXIST);
        }

        if (!userAccount.getActiveCode().equals(user.getActiveCode())) {
            throw new UserException(UserEnum.CODE_NOT_TRUE);
        }

        userAccount.setState(1);

        return repository.save(userAccount);
    }
}
