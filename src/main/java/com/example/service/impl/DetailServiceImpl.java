package com.example.service.impl;

import com.example.dataobject.UserDetail;
import com.example.repository.DetailRepository;
import com.example.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/4/4.
 */
@Service
public class DetailServiceImpl implements DetailService {

    @Autowired
    DetailRepository repository;

    @Override
    public UserDetail findOne(String detailId) {
        return repository.findOne(detailId);
    }
}
