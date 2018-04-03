package com.example.controller;

import com.example.dataobject.AssignmentInfo;
import com.example.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */
@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @RequestMapping("/list")
    public void list(){
        List<AssignmentInfo> assignmentInfoList = assignmentService.findAllUnReceive();


    }
}
