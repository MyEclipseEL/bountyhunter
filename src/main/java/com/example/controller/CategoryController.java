package com.example.controller;

import com.example.VO.AssignmentInfoVO;
import com.example.VO.AssignmentVO;
import com.example.VO.ResultVO;
import com.example.VO.UserAccountVO;
import com.example.converter.AssignmentInfoList2VOlistConverter;
import com.example.dataobject.AssignmentCategory;
import com.example.dataobject.AssignmentInfo;
import com.example.dataobject.UserAccount;
import com.example.dataobject.UserDetail;
import com.example.enums.AssignmentStatus;
import com.example.enums.ResultEnum;
import com.example.exception.HunterException;
import com.example.service.AssignmentCategoryService;
import com.example.service.AssignmentService;
import com.example.service.DetailService;
import com.example.service.UserAccountService;
import com.example.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/4/9.
 */
//@RestController
@RequestMapping("/category")
@Slf4j
//@CrossOrigin
@Controller
public class CategoryController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentCategoryService categoryService;

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private DetailService detailService;

    /**
     *
     * */
    @GetMapping("/list")
    public String category(@RequestParam(value = "categoryType" ,required = false) Integer categoryType,Model model ){
        List<AssignmentInfo> assignmentInfoList = new ArrayList<>();
        AssignmentCategory categoryThis = new AssignmentCategory() ;
        //判断是否传类别
        if (categoryType==null){
            assignmentInfoList = assignmentService.findByAssignmentStatus(AssignmentStatus.NEW.getCode());

        }else {
            assignmentInfoList = assignmentService.findByCategoryTypeAndAssignmentStatus(categoryType,AssignmentStatus.NEW.getCode());
            categoryThis = categoryService.findByCategory(categoryType);

        }


        List<AssignmentCategory> categoryList = categoryService.findAll();

        AssignmentInfoList2VOlistConverter converter = new AssignmentInfoList2VOlistConverter();
        //组装数据
        List<AssignmentInfoVO> resultVOList = converter.converter(assignmentInfoList,accountService,detailService);

        model.addAttribute("categoryThis",categoryThis);
        model.addAttribute("results",resultVOList);
        model.addAttribute("category",categoryList);
        return "products";
    }

    /**
     * 查找最新任务
     * */
    @GetMapping("/news")
    public String news(Model model){
        AssignmentInfoList2VOlistConverter converter = new AssignmentInfoList2VOlistConverter();
        List<AssignmentInfo> assignmentInfoList = assignmentService.findTheNWE();
        List<AssignmentInfoVO> resultVOList = converter.converter(assignmentInfoList,accountService,detailService);
        model.addAttribute("results",resultVOList);
        findType(model);
        return "furniture";
    }

    //获取所有类别
    public void findType(Model model){
        List<AssignmentCategory> categoryList = categoryService.findAll();
        model.addAttribute("category",categoryList);
    }

    @GetMapping("/mail")
    public String mailType(Model model){
        findType(model);
        return "mail";
    }

    @GetMapping("/check")
    public String checkType(Model model){
        findType(model);
        return "checkout";
    }

    @GetMapping("/detail{assignmentId}")
    public String detail(@RequestParam("assignmentId") String assignmentId,
                         Model model){
        if(assignmentId.equals("")){
            log.error("【任务详情】参数错误: assignmentId={}",assignmentId);
            throw new HunterException(ResultEnum.PARAM_ERROR);
        }
        AssignmentInfo assignmentInfo = assignmentService.findOne(assignmentId);
        if (assignmentInfo==null){
            log.error("【任务详情】任务不存在：assignmentId={}",assignmentId);
            throw new HunterException(ResultEnum.ASSIGNMENT_NOT_EXIST);
        }
        UserAccount account = accountService.findOne(assignmentInfo.getAssignmentOwner());

        UserDetail detail = detailService.findOne(account.getDetailId());

        AssignmentInfoVO assignmentInfoVO = new AssignmentInfoVO();
        BeanUtils.copyProperties(assignmentInfo,assignmentInfoVO);

        UserAccountVO accountVO = new UserAccountVO();
        BeanUtils.copyProperties(account,accountVO);

        accountVO.setUserDetail(detail);
        assignmentInfoVO.setAssignmentOwner(accountVO);
        List<AssignmentCategory> categoryList = categoryService.findAll();
        model.addAttribute("category",categoryList);
        model.addAttribute("result",assignmentInfoVO);

        return "single";
    }

    @GetMapping("/receive{assignmentId}")
    public String receive(@RequestParam("assignmentId") String assignmentId,
                                            HttpServletRequest request,
                                            Model model) {
        HttpSession session = request.getSession();
//        String accountId = (String) session.getAttribute("account");
//        String accountId = (String) session.getValue("account");
        String accountId = "";
        Cookie cookies[] = request.getCookies();
        List<AssignmentCategory> categoryList = categoryService.findAll();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("account")) {
                accountId = cookie.getValue();
                break;
            }
        }
        model.addAttribute("category",categoryList);
        log.info(accountId);
        if (accountId==null||accountId.isEmpty()){
            log.error("【接取任务】用户信息为空 session={}",session.getAttributeNames());
            model.addAttribute("msg","尚未登陆或登陆失效，请重新登陆！");
            return "login";
//            throw new HunterException(ResultEnum.ACCOUNT_EMPTY);
        }
        UserAccount account = accountService.findOne(accountId);
        if (account==null){
            model.addAttribute("msg","尚未登陆或登陆失效，请重新登陆！");
            log.error("【接取任务】用户不存在 accountId={}",accountId);
            return "login";
//            throw new HunterException(ResultEnum.ACCOUNT_NOT_EXIST);
        }
        if (assignmentId.isEmpty()){
            log.error("【接取任务】参数错误 assignmentId={}",assignmentId);
            model.addAttribute("code",-1);
            model.addAttribute("msg","出错了，稍后试试");
            return "redirect:single";
//            throw new HunterException(ResultEnum.PARAM_ERROR);
        }
        AssignmentInfo assignmentInfo = assignmentService.findOne(assignmentId);
        if (assignmentInfo==null){
            log.error("【接取任务】任务不存在 assignmentId={}",assignmentId);
            throw new HunterException(ResultEnum.ASSIGNMENT_NOT_EXIST);
        }
        if (!assignmentInfo.getAssignmentStatus().equals(AssignmentStatus.NEW.getCode())){
            log.error("【接取任务】出错了 任务已被接取 assignmentStatus={}",assignmentInfo.getAssignmentStatus());
            throw new HunterException(ResultEnum.RECEIVE_EXIST);
        }
        assignmentInfo.setAssignmentStatus(AssignmentStatus.RECEIVED.getCode());
        assignmentInfo.setAssignmentReceive(account.getAccountId());

        AssignmentInfo result = assignmentService.save(assignmentInfo);

        return "redirect:/single.html";
    }



}
