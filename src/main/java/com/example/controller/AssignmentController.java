package com.example.controller;

import com.example.VO.AssignmentInfoVO;
import com.example.VO.AssignmentVO;
import com.example.VO.ResultVO;
import com.example.VO.UserAccountVO;
import com.example.converter.AssignmentForm2InfoConverter;
import com.example.dataobject.AssignmentCategory;
import com.example.dataobject.AssignmentInfo;
import com.example.dataobject.UserAccount;
import com.example.dataobject.UserDetail;
import com.example.enums.ResultEnum;
import com.example.exception.HunterException;
import com.example.form.AssignmentForm;
import com.example.service.AssignmentCategoryService;
import com.example.service.AssignmentService;
import com.example.service.DetailService;
import com.example.service.UserAccountService;
import com.example.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/4/2.
 */
@RestController
@RequestMapping("/assignment")
@Slf4j
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentCategoryService categoryService;

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private DetailService detailService;

    @PostMapping("/issue")
    public ResultVO<Map<String,String>> issue(@Valid AssignmentForm assignmentForm,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            log.error("【发布任务】参数错误 assignmentForm={}",assignmentForm);
            throw new HunterException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        AssignmentInfo assignmentInfo = AssignmentForm2InfoConverter.converter(assignmentForm);

        AssignmentInfo result = assignmentService.save(assignmentInfo);
        Map<String,String> map = new HashMap<>();
        map.put("assignmentId",result.getAssignmentId());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/list")
    public ResultVO list(){
        //1.查询所有未接任务
        List<AssignmentInfo> assignmentInfoList = assignmentService.findAllUnReceive();

        //2.查询任务类别
        List<Integer> categoryTypeList = assignmentInfoList.stream()
                .map(e ->e.getCategoryType())
                .collect(Collectors.toList());
        List<AssignmentCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.查询任务发布者
//        List<String> accountIdList = assignmentInfoList.stream()
//                .map(e->e.getAssignmentOwner())
//                .collect(Collectors.toList());
//        List<UserAccount> accountList = accountService.findByAccountIdIn(accountIdList);

        //4.数据拼装
        List<AssignmentVO> resultVOList = new ArrayList<>();
        for(AssignmentCategory category:categoryList){
            AssignmentVO assignmentVO = new AssignmentVO();
            assignmentVO.setCategoryName(category.getCategoryName());
            assignmentVO.setCategoryType(category.getCategoryType());

            List<AssignmentInfoVO> assignmentInfoVOList = new ArrayList<>();
            for (AssignmentInfo assignmentInfo:assignmentInfoList){
                if (assignmentInfo.getCategoryType().equals(category.getCategoryType())){
                    AssignmentInfoVO assignmentInfoVO = new AssignmentInfoVO();

                    UserAccount account =  accountService.findOne(assignmentInfo.getAssignmentOwner());
                    UserAccountVO accountVO = new UserAccountVO();
                    BeanUtils.copyProperties(account,accountVO);
                    UserDetail detail = detailService.findOne(account.getDetailId());
                    accountVO.setUserDetail(detail);

                    BeanUtils.copyProperties(assignmentInfo,assignmentInfoVO);
                    assignmentInfoVO.setAssignmentOwner(accountVO);

                    assignmentInfoVOList.add(assignmentInfoVO);
                }
            }

            assignmentVO.setAssignmentInfoVOList(assignmentInfoVOList);
            resultVOList.add(assignmentVO);

        }
        return ResultVOUtil.success(resultVOList);
    }

    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("assignmentOwner") String assignmentOwner,
                       @RequestParam("assignmentId") String assignmentId){
        AssignmentInfo assignmentInfo = assignmentService.findOne(assignmentId);
        UserAccount account = accountService.findOne(assignmentOwner);
        UserDetail detail = detailService.findOne(account.getDetailId());

        AssignmentInfoVO assignmentInfoVO = new AssignmentInfoVO();
        BeanUtils.copyProperties(assignmentInfo,assignmentInfoVO);

        UserAccountVO accountVO = new UserAccountVO();
        BeanUtils.copyProperties(account,accountVO);

        accountVO.setUserDetail(detail);
        assignmentInfoVO.setAssignmentOwner(accountVO);

        return ResultVOUtil.success(assignmentInfoVO);
    }
}
