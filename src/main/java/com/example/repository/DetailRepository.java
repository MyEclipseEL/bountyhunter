package com.example.repository;

import com.example.dataobject.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/4/4.
 */
public interface DetailRepository extends JpaRepository<UserDetail,String> {
}
