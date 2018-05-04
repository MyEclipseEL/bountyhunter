package com.example.converter;

import com.example.dataobject.AssignmentInfo;
import com.example.form.AssignmentForm;
import com.example.util.KeyUtil;

/**
 * Created by Administrator on 2018/4/7.
 */
public class AssignmentForm2InfoConverter {

    public static AssignmentInfo converter(AssignmentForm assignmentForm){
        AssignmentInfo assignmentInfo = new AssignmentInfo();

//        assignmentInfo.setAssignmentId(assignmentForm.getId());
        assignmentInfo.setAssignmentName(assignmentForm.getName());
        assignmentInfo.setCategoryType(assignmentForm.getType());
        assignmentInfo.setAssignmentDescription(assignmentForm.getDescription());
        assignmentInfo.setAssignmentIcon(assignmentForm.getIcon());
        assignmentInfo.setAssignmentPosition(assignmentForm.getPosition());
        assignmentInfo.setAssignmentReward(assignmentForm.getReward());

        return assignmentInfo;
    }
}
