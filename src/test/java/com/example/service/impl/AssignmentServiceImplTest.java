package com.example.service.impl;

import com.example.dataobject.AssignmentInfo;
import com.example.service.AssignmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/5/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AssignmentServiceImplTest {

    @Autowired
    private AssignmentService assignmentService;

    @Test
    public void findTheNWE() throws Exception {
        List<AssignmentInfo> assignmentInfoList = assignmentService.findTheNWE();
        log.info("【Test】 assignmentInfoList={}",assignmentInfoList);
    }

}