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
import com.example.util.ImageUtil;
import com.example.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/4/2.
 */
@Controller
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
    /**
     * 在配置文件中配置的图片保存路径
     */
    @Value("${img.location}")
    private String location;

    @RequestMapping(value = "/issue")
    @ResponseBody
    public ResultVO<Map<String,String>> issue(
                @Valid AssignmentForm assignmentForm,
                BindingResult bindingResult,
                @RequestParam("file") MultipartFile file,
                HttpServletRequest request) {

        HttpSession session = request.getSession();
        UserAccount userAccount = (UserAccount) session.getAttribute("userAccount");
        if (userAccount==null){
            log.error("【发布任务】用户信息为空");
            throw new HunterException(ResultEnum.ACCOUNT_EMPTY);
        }
        String icon = "";
        try {
            icon =fileUpload(file,userAccount);
        }catch (NullPointerException e){
            log.error("图片为空");
            throw new HunterException(ResultEnum.PARAM_ERROR);
        }

        log.info("assignment={}",assignmentForm);
        if (bindingResult.hasErrors()){
            log.error("【发布任务】参数错误 assignmentForm={}",assignmentForm);
            throw new HunterException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        String accountId = userAccount.getAccountId();
        UserAccount account = accountService.findOne(accountId);
        if (account==null){
            log.error("【发布任务】用户不存在 accountId={}",accountId);
            throw new HunterException(ResultEnum.ACCOUNT_NOT_EXIST);
        }

        AssignmentInfo assignmentInfo = AssignmentForm2InfoConverter.converter(assignmentForm);
        assignmentInfo.setAssignmentOwner(account.getAccountId());
        assignmentInfo.setAssignmentIcon(icon);
        AssignmentInfo result = assignmentService.save(assignmentInfo);
        Map<String,String> map = new HashMap<>();
        map.put("assignmentId",result.getAssignmentId());
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

    /**
     *分类任务列表
     */

    @GetMapping("/list")
    public String list(@RequestParam(value = "categoryType") Integer categoryType,
                       @RequestParam(value = "page" ,defaultValue = "0") Integer page,
                       @RequestParam(value = "size" ,defaultValue = "9") Integer size,
                       Model model){
        List<AssignmentCategory> categoryList = categoryService.findAll();
        model.addAttribute("category",categoryList);
        if (StringUtils.isEmpty(categoryType)){
            log.error("【查询任务】没有收到categoryType");
            model.addAttribute("msg","出错了");
            return "redirect:/index";

        }
        PageRequest request = new PageRequest(page,size);
        Page<AssignmentInfoVO> infoVOPage = assignmentService.findList(categoryType,request);
        log.info("infoPage={}",infoVOPage);
        AssignmentCategory categoryThis = categoryService.findByCategory(categoryType);
        Integer indexPage = page;
        Integer totalPages = infoVOPage.getTotalPages();
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("indexPage",indexPage);
        model.addAttribute("categoryThis",categoryThis);
        model.addAttribute("infoPage",infoVOPage);
        return "products";
    }

    @GetMapping("/history")
    public String history(@RequestParam(value = "page" ,defaultValue = "0") Integer page,
                          @RequestParam(value = "size" ,defaultValue = "10") Integer size,
                          HttpServletRequest request,
                          Model model){

        HttpSession session = request.getSession();
        UserAccount account = (UserAccount) session.getAttribute("userAccount");
        if (account == null){
            return "redirect:/index";
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<AssignmentInfoVO> voPage = assignmentService.findUserHistoryAssignment(account.getAccountId(),pageRequest);
        Integer indexPage = page;
        Integer totalPages = voPage.getTotalPages();
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("indexPage",indexPage);
        model.addAttribute("infoPage",voPage);
        return "checkout";
    }

    /**
     * 实现文件上传
     * */
//    @RequestMapping(value="fileUpload",method = RequestMethod.POST)
//    @ResponseBody
    public String fileUpload( MultipartFile file,UserAccount userAccount){

        if(file.isEmpty()){
            return null;
        }
        String root_fileName = file.getOriginalFilename();

//        String path = System.getProperty("user.dir") + "/uploadFile" ;
//        File dest = new File(path + "/" + fileName);
//        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
//            dest.getParentFile().mkdir();
//        }
        String return_path = ImageUtil.getFilePath(userAccount);
        String filePath = location + return_path;
        String file_name = "";
        try{
            file_name = ImageUtil.saveImg(file,filePath);
            if (org.apache.commons.lang3.StringUtils.isNoneBlank(file_name)){
                return return_path + "/" + file_name;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            file.transferTo(dest); //保存文件
//            return dest.getPath();
//        } catch (IllegalStateException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }
}
