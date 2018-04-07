package com.example.service.impl;

import com.example.dataobject.AssignmentInfo;
import com.example.enums.AssignmentStatus;
import com.example.enums.PayStatus;
import com.example.repository.AssignmentRepository;
import com.example.service.AssignmentService;
import com.example.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */
@Service
public class AssignmentServiceImpl implements AssignmentService{

    @Autowired
    private AssignmentRepository repository;

    @Override
    public AssignmentInfo findOne(String assignmentId) {
        return repository.findOne(assignmentId);
    }

    @Override
    public List<AssignmentInfo> findByCategoryType(Integer categoryType) {
        return repository.findByCategoryType(categoryType);
    }

    @Override
    public List<AssignmentInfo> findByAssignmentStatus(Integer assignmentStatus) {
        return repository.findByAssignmentStatus(assignmentStatus);
    }

    @Override
    public List<AssignmentInfo> findByPayStatus(Integer payStatus) {
        return repository.findByPayStatus(payStatus);
    }

    @Override
    public List<AssignmentInfo> findByAssignmentOwner(String assignmentOwner) {
        return repository.findByAssignmentOwner(assignmentOwner);
    }

    @Override
    public List<AssignmentInfo> findByAssignmentReceive(String assignmentReceive) {
        return repository.findByAssignmentReceive(assignmentReceive);
    }

    @Override
    public List<AssignmentInfo> findAllUnReceive() {
        return repository.findAll();
    }

    @Override
    public AssignmentInfo save(AssignmentInfo assignmentInfo) {
        // id为空 为新增 添加id,状态
        if (assignmentInfo.getAssignmentId()==null){
            assignmentInfo.setAssignmentId(KeyUtil.genUniqueKey());
            assignmentInfo.setAssignmentStatus(AssignmentStatus.NEW.getCode());
            assignmentInfo.setPayStatus(PayStatus.WAIT.getCode());
        }
        return repository.save(assignmentInfo);
    }
}
