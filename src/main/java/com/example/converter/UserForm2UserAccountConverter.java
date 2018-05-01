package com.example.converter;

import com.example.dataobject.UserAccount;
import com.example.form.UserForm;

/**
 * Created by 白 on 2018/4/26.
 */
public class UserForm2UserAccountConverter {

    public static UserAccount converter(UserForm userForm) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUserName(userForm.getName());
        userAccount.setUserEmail(userForm.getEmail());
        userAccount.setUserPassword(userForm.getPassword());

        return userAccount;
    }
}
