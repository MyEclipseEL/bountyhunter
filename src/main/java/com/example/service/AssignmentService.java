package com.example.service;

import com.example.dataobject.AssignmentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */
public interface AssignmentService {

    AssignmentInfo findOne(String assignmentId);

//    AssignmentInfo findOne(String assignmentId,Integer assignmentStatus);

    List<AssignmentInfo> findByCategoryType(Integer categoryType);

    /**
     * 通过类别以及任务状态查找
     * @param categoryType
     * @param assignmentStatus
     * @return
     * */
    List<AssignmentInfo> findByCategoryTypeAndAssignmentStatus(Integer categoryType,Integer assignmentStatus);

    List<AssignmentInfo> findByAssignmentStatus(Integer assignmentStatus);

    List<AssignmentInfo> findByPayStatus(Integer payStatus);

    List<AssignmentInfo> findByAssignmentOwner(String assignmentOwner);

    List<AssignmentInfo> findByAssignmentReceive(String assignmentReceive);

    List<AssignmentInfo> findAllUnReceive(Integer status);

    /** 修改任务状态*/

    /** 修改支付状态*/

    /** 增加和更新*/
    AssignmentInfo save(AssignmentInfo assignmentInfo);




}
