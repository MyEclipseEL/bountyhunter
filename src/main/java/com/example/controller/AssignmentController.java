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
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
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
@CrossOrigin
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentCategoryService categoryService;

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private DetailService detailService;

    @RequestMapping("/issue")
    public ResultVO<Map<String,String>> issue(
                                              @Valid AssignmentForm assignmentForm,
                                              BindingResult bindingResult,
                                                HttpServletRequest request) {
        log.info("assignment={}",assignmentForm);
        if (bindingResult.hasErrors()){
            log.error("【发布任务】参数错误 assignmentForm={}",assignmentForm);
            throw new HunterException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        HttpSession session = request.getSession();
        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
        String accountId = userAccount.getAccountId();

        if (accountId==null||accountId.isEmpty()){
            log.error("【发布任务】用户信息为空");
            throw new HunterException(ResultEnum.ACCOUNT_EMPTY);
        }
        UserAccount account = accountService.findOne(accountId);
        if (account==null){
            log.error("【发布任务】用户不存在 accountId={}",accountId);
            throw new HunterException(ResultEnum.ACCOUNT_NOT_EXIST);
        }

//        AssignmentForm assignmentForm = new AssignmentForm();
        AssignmentInfo assignmentInfo = AssignmentForm2InfoConverter.converter(assignmentForm);
        assignmentInfo.setAssignmentOwner(account.getAccountId());

        AssignmentInfo result = assignmentService.save(assignmentInfo);
        Map<String,String> map = new HashMap<>();
        map.put("assignmentId",result.getAssignmentId());
//        response.setHeader( "Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");//
//        response.addHeader( "Access-Control-Allow-Origin", "*" ); //可以访问此域资源的域。*为所有
//        response.addHeader( "Access-Control-Allow-Methods", "POST" ); //可以访问此域的脚本方法类型
//        response.addHeader( "Access-Control-Max-Age", "1000" ); //
        return ResultVOUtil.success(map);
    }

    /**
     * 主页悬赏任务
     * */


    /**
     * 取消任务 若任务未被接取 则发布者可以取消 若被接取则不能取消
     * @param assignmentId
     * @param request
     * */

    @GetMapping("/cancel")
    public ResultVO<AssignmentInfo> cancel(@RequestParam("assignmentId") String assignmentId,
                       HttpServletRequest request){
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute("account");
        if (accountId==null||accountId.isEmpty()){
            log.error("【取消任务】出错了 没有用户信息");
            throw new HunterException(ResultEnum.ACCOUNT_EMPTY);
        }
        UserAccount account = accountService.findOne(accountId);
        if (account==null){
            log.error("【取消任务】用户不存在 accountId={}",accountId);
            throw new HunterException(ResultEnum.ACCOUNT_NOT_EXIST);
        }
        if (assignmentId==null||assignmentId.isEmpty()){
            log.error("【取消任务】没有传任务id");
            throw new HunterException(ResultEnum.PARAM_ERROR);
        }
        AssignmentInfo assignmentInfo = assignmentService.findOne(assignmentId);
        if (assignmentInfo==null){
            log.error("【取消任务】任务不存在 assignmentId={}",assignmentId);
            throw new HunterException(ResultEnum.ASSIGNMENT_NOT_EXIST);
        }
        if (!assignmentInfo.getAssignmentStatus().equals(AssignmentStatus.NEW.getCode())){
            log.error("【取消任务】任务状态错误 assignmentStatus={}",assignmentInfo.getAssignmentStatus());
        }
        assignmentInfo.setAssignmentStatus(AssignmentStatus.CANCEL.getCode());
        return ResultVOUtil.success(assignmentService.save(assignmentInfo));
    }

    /**
     * 完结任务
     * */


}
