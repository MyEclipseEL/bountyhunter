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
import com.example.enums.AssignmentStatus;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
                                                HttpServletRequest request,
                                              BindingResult bindingResult) {
        HttpSession session = request.getSession();
        UserAccount account = (UserAccount) session.getAttribute("account");
        if (bindingResult.hasErrors()){
            log.error("【发布任务】参数错误 assignmentForm={}",assignmentForm);
            throw new HunterException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        AssignmentInfo assignmentInfo = AssignmentForm2InfoConverter.converter(assignmentForm);
        assignmentInfo.setAssignmentOwner(account.getAccountId());

        AssignmentInfo result = assignmentService.save(assignmentInfo);
        Map<String,String> map = new HashMap<>();
        map.put("assignmentId",result.getAssignmentId());
        return ResultVOUtil.success(map);
    }

    /**
     * 主页悬赏任务
     * */

}
