package com.example.repository;

import com.example.dataobject.AssignmentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */
public interface AssignmentRepository extends JpaRepository<AssignmentInfo,String>,JpaSpecificationExecutor<AssignmentInfo>{

//    AssignmentInfo findByAssignmentId(String assignmentId);

    List<AssignmentInfo> findByCategoryType(Integer categoryType);

    List<AssignmentInfo> findByCategoryTypeAndAssignmentStatus(Integer categoryType,Integer assignmentStatus);

    List<AssignmentInfo> findByAssignmentStatus(Integer assignmentStatus);

    List<AssignmentInfo> findByPayStatus(Integer payStatus);

    List<AssignmentInfo> findByAssignmentOwner(String AssignmentOwner);


    List<AssignmentInfo> findByAssignmentOwnerAndAssignmentStatus(String AssignmentOwner,Integer assignmentStatus);


    List<AssignmentInfo> findByAssignmentReceive(String AssignmentReceive);

    Page<AssignmentInfo> findByAssignmentStatusAndCategoryType(Integer status, Integer categoryType, Pageable pageable);

    Page<AssignmentInfo> findByAssignmentOwner(String account,Pageable pageable);

    Page<AssignmentInfo> findByAssignmentReceive(String account,Pageable pageable);

    List<AssignmentInfo> findByAssignmentStatusOrderByCreateTime(Integer status);
}
