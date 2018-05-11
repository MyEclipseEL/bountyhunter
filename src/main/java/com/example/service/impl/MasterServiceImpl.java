package com.example.service.impl;

import com.example.dataobject.AssignmentInfo;
import com.example.dataobject.UserAccount;
import com.example.repository.AssignmentRepository;
import com.example.repository.UserAccountRepository;
import com.example.service.MasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/5/12.
 */
@Service
@Slf4j
public class MasterServiceImpl implements MasterService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public Page<AssignmentInfo> findAllAssignments(Pageable pageable) {
        Page<AssignmentInfo> infoPage = assignmentRepository.findAll(pageable);
        return infoPage;
    }

    @Override
    public Page<UserAccount> findAllUsers(Pageable pageable) {
        Page<UserAccount> accountPage = userAccountRepository.findAll(pageable);
        return accountPage;
    }

    @Override
    public void deleteAssignment(String assignmentId) {
        assignmentRepository.delete(assignmentId);
    }

    @Override
    public void deleteUser(String accountId) {
        userAccountRepository.delete(accountId);
    }
}
