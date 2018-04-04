package com.example.service;

import com.example.dataobject.UserDetail;

/**
 * Created by Administrator on 2018/4/4.
 */
public interface DetailService {

    UserDetail findOne(String detailId);
}
