package com.example.service;

import com.example.dataobject.AssignmentInfo;
import com.example.dataobject.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Administrator on 2018/5/12.
 */
public interface MasterService {

    Page<AssignmentInfo> findAllAssignments(Pageable pageable);

    Page<UserAccount> findAllUsers(Pageable pageable);

    void deleteAssignment(String assignmentId);

    void deleteUser(String accountId);


}
