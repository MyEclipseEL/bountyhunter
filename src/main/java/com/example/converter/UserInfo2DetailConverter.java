package com.example.converter;

import com.example.dataobject.UserDetail;
import com.example.form.UserInfoForm;

/**
 * Created by 白 on 2018/5/6.
 */
public class UserInfo2DetailConverter {

    public static UserDetail converter(UserInfoForm infoForm, String id) {
        UserDetail detail = new UserDetail();
        detail.setDetailId(id);
        detail.setUserPhone(infoForm.getPhone());
        detail.setUserAddress(infoForm.getAddress());
        detail.setUserBirthday(infoForm.getBirthday());

        return detail;
    }
}
