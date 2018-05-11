package com.example.service;

import com.example.dataobject.AssignmentInfo;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2018/5/12.
 */
public interface MasterService {

    Page<AssignmentInfo> findAllAssignments();


}
