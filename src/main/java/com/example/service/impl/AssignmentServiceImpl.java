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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public List<AssignmentInfo> findByCategoryTypeAndAssignmentStatus(Integer categoryType,Integer assignmentStatus) {
        return repository.findByCategoryTypeAndAssignmentStatus(categoryType,assignmentStatus);
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
    public List<AssignmentInfo> findAllUnReceive(Integer status) {
        return repository.findByAssignmentStatus(status);
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

    @Override
    public List<AssignmentInfo> findOrderByReward() {
        List<AssignmentInfo> assignmentInfoList = repository.findByAssignmentStatus(AssignmentStatus.NEW.getCode());
        for (int i=0;i<assignmentInfoList.size();i++){
            for (int j=1;j<assignmentInfoList.size();j++){
                if (assignmentInfoList.get(j-1).getAssignmentReward().
                        compareTo(assignmentInfoList.get(j).getAssignmentReward())==-1){
                    AssignmentInfo assignmentInfo = assignmentInfoList.get(j-1);
                    assignmentInfoList.set(j-1,assignmentInfoList.get(j));
                    assignmentInfoList.set(j,assignmentInfo);
                }
            }
        }
        return assignmentInfoList;
    }

    /**
     * 查找发布时间距离当前 3 小时以内的所有任务
     * */
    @Override
    public List<AssignmentInfo> findTheNWE() {
        List<AssignmentInfo> assignmentInfoList = repository.findByAssignmentStatus(AssignmentStatus.NEW.getCode());
        List<AssignmentInfo> assignmentInfos = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(AssignmentInfo assignmentInfo:assignmentInfoList){
            try {
                Date This = format.parse(assignmentInfo.getCreateTime().toString());
                Date Now = format.parse(format.format(System.currentTimeMillis()));
                if (Now.getTime() - This.getTime() < 1000*3600*3){
                    assignmentInfos.add(assignmentInfo);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return assignmentInfos;

    }
}
