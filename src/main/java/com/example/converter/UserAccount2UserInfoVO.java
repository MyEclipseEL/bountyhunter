package com.example.converter;

import com.example.VO.UserInfoVO;
import com.example.dataobject.UserAccount;
import com.example.dataobject.UserDetail;
import com.example.form.UserForm;

/**
 * Created by 白 on 2018/5/10.
 */
public class UserAccount2UserInfoVO {

    public static UserInfoVO converter(UserAccount userAccount, UserDetail detail) {
        UserInfoVO userInfoVO = new UserInfoVO();

        userInfoVO.setName(userAccount.getUserName());
        userInfoVO.setEmail(userAccount.getUserEmail());
        userInfoVO.setPassword(userAccount.getUserPassword());
        userInfoVO.setIcon(userAccount.getIcon());

        userInfoVO.setPhone(detail.getUserPhone());
        userInfoVO.setAddress(detail.getUserAddress());
        userInfoVO.setBirthday(detail.getUserBirthday());
        return userInfoVO;
    }
}
