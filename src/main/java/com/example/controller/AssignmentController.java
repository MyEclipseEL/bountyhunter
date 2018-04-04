package com.example.controller;

import com.example.VO.AssignmentInfoVO;
import com.example.VO.AssignmentVO;
import com.example.VO.ResultVO;
import com.example.dataobject.AssignmentCategory;
import com.example.dataobject.AssignmentInfo;
import com.example.service.AssignmentCategoryService;
import com.example.service.AssignmentService;
import com.example.util.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/4/2.
 */
@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentCategoryService categoryService;

    @RequestMapping("/list")
    public ResultVO list(){
        //1.查询所有未接任务
        List<AssignmentInfo> assignmentInfoList = assignmentService.findAllUnReceive();

        //2.查询任务类别
        List<Integer> categoryTypeList = assignmentInfoList.stream()
                .map(e ->e.getCategoryType())
                .collect(Collectors.toList());
        List<AssignmentCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装
        List<AssignmentVO> resultVOList = new ArrayList<>();
        for(AssignmentCategory category:categoryList){
            AssignmentVO assignmentVO = new AssignmentVO();
            assignmentVO.setCategoryName(category.getCategoryName());
            assignmentVO.setCategoryType(category.getCategoryType());

            List<AssignmentInfoVO> assignmentInfoVOList = new ArrayList<>();
            for (AssignmentInfo assignmentInfo:assignmentInfoList){
                if (assignmentInfo.getCategoryType().equals(category.getCategoryType())){
                    AssignmentInfoVO assignmentInfoVO = new AssignmentInfoVO();
                    BeanUtils.copyProperties(assignmentInfo,assignmentInfoVO);
                    assignmentInfoVOList.add(assignmentInfoVO);
                }
            }

            assignmentVO.setAssignmentInfoVOList(assignmentInfoVOList);
            resultVOList.add(assignmentVO);

        }
        return ResultVOUtil.success(resultVOList);
    }

    public void issue(){

    }
}
