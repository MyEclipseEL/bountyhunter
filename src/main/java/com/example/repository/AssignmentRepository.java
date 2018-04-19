package com.example.repository;

import com.example.dataobject.AssignmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */
public interface AssignmentRepository extends JpaRepository<AssignmentInfo,String>{

//    AssignmentInfo findByAssignmentId(String assignmentId);

    List<AssignmentInfo> findByCategoryType(Integer categoryType);

    List<AssignmentInfo> findByCategoryTypeAndAssignmentStatus(Integer categoryType,Integer assignmentStatus);

    List<AssignmentInfo> findByAssignmentStatus(Integer assignmentStatus);

    List<AssignmentInfo> findByPayStatus(Integer payStatus);

    List<AssignmentInfo> findByAssignmentOwner(String AssignmentOwner);

    List<AssignmentInfo> findByAssignmentOwnerAndAssignmentStatus(String AssignmentOwner,Integer assignmentStatus);


    List<AssignmentInfo> findByAssignmentReceive(String AssignmentReceive);
}