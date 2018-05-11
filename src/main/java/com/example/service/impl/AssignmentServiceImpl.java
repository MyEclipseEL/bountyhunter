package com.example.service.impl;

import com.example.VO.AssignmentInfoVO;
import com.example.converter.AssignmentInfoList2VOlistConverter;
import com.example.dataobject.AssignmentInfo;
import com.example.dataobject.AssignmentQuery;
import com.example.enums.AssignmentStatus;
import com.example.enums.PayStatus;
import com.example.enums.ReceiveStatus;
import com.example.repository.AssignmentRepository;
import com.example.service.AssignmentService;
import com.example.service.DetailService;
import com.example.service.UserAccountService;
import com.example.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */
@Service
public class AssignmentServiceImpl implements AssignmentService{

    @Autowired
    private AssignmentRepository repository;

    @Autowired
    private UserAccountService accountService;

    @Autowired
    private DetailService detailService;

    @Override
    public AssignmentInfo findOne(String assignmentId) {
        return repository.findOne(assignmentId);
    }

    @Override
    public List<AssignmentInfo> findByCategoryType(Integer categoryType) {
        return repository.findByCategoryType(categoryType);
    }


    @Override
    public List<AssignmentInfo> findByCategoryTypeAndAssignmentStatus(Integer categoryType,Integer assignmentStatus) {
        return repository.findByCategoryTypeAndAssignmentStatus(categoryType,assignmentStatus);
    }

    @Override
    public List<AssignmentInfo> findByAssignmentStatus(Integer assignmentStatus) {
        return repository.findByAssignmentStatus(assignmentStatus);
    }

    @Override
    public List<AssignmentInfo> findByPayStatus(Integer payStatus) {
        return repository.findByPayStatus(payStatus);
    }

    @Override
    public List<AssignmentInfo> findByAssignmentOwner(String assignmentOwner) {
        return repository.findByAssignmentOwner(assignmentOwner);
    }

    @Override
    public List<AssignmentInfo> findByAssignmentReceive(String assignmentReceive) {
        return repository.findByAssignmentReceive(assignmentReceive);
    }

    @Override
    public List<AssignmentInfo> findAllUnReceive(Integer status) {
        return repository.findByAssignmentStatus(status);
    }

    @Override
    public AssignmentInfo save(AssignmentInfo assignmentInfo) {
        // id为空 为新增 添加id,状态
        if (assignmentInfo.getAssignmentId()==null){
            assignmentInfo.setAssignmentId(KeyUtil.genUniqueKey());
            assignmentInfo.setAssignmentStatus(AssignmentStatus.NEW.getCode());
            assignmentInfo.setReceiveStatus(ReceiveStatus.NEW.getCode());
            assignmentInfo.setPayStatus(PayStatus.WAIT.getCode());
        }
        return repository.save(assignmentInfo);
    }

    @Override
    public List<AssignmentInfo> findOrderByReward(int index) {
        List<AssignmentInfo> assignmentInfoList = repository.findByAssignmentStatus(AssignmentStatus.NEW.getCode());
        for (int i=0;i<assignmentInfoList.size();i++){
            for (int j=1;j<assignmentInfoList.size();j++){
                if (assignmentInfoList.get(j-1).getAssignmentReward().
                        compareTo(assignmentInfoList.get(j).getAssignmentReward())==-1){
                    AssignmentInfo assignmentInfo = assignmentInfoList.get(j-1);
                    assignmentInfoList.set(j-1,assignmentInfoList.get(j));
                    assignmentInfoList.set(j,assignmentInfo);
                }
            }
        }
       while (assignmentInfoList.size()>index){
           assignmentInfoList.remove(index);
       }
        return assignmentInfoList;
    }

    @Override
    public List<AssignmentInfo> findOrderByReward(int index, Integer categoryType) {
        List<AssignmentInfo> assignmentInfoList = repository.findByAssignmentStatus(AssignmentStatus.NEW.getCode());
        for (int i=0;i<assignmentInfoList.size();i++){
            if (assignmentInfoList.get(i).getCategoryType() .compareTo(categoryType)==0 ){
                assignmentInfoList.remove(i);
            }
            for (int j=1;j<assignmentInfoList.size();j++){
                if (assignmentInfoList.get(j-1).getAssignmentReward().
                        compareTo(assignmentInfoList.get(j).getAssignmentReward())==-1) {
                    AssignmentInfo assignmentInfo = assignmentInfoList.get(j - 1);
                    assignmentInfoList.set(j - 1, assignmentInfoList.get(j));
                    assignmentInfoList.set(j, assignmentInfo);
                }
            }
        }
        while (assignmentInfoList.size()>index){
            assignmentInfoList.remove(index);
        }

        return assignmentInfoList;
    }

    /**
     * 查找发布时间距离当前 3 小时以内的所有任务
     * */
    @Override
    public List<AssignmentInfo> findTheNWE() {
        List<AssignmentInfo> assignmentInfoList = repository.findByAssignmentStatus(AssignmentStatus.NEW.getCode());
        List<AssignmentInfo> assignmentInfos = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(AssignmentInfo assignmentInfo:assignmentInfoList){
            try {
                Date This = format.parse(assignmentInfo.getCreateTime().toString());
                Date Now = format.parse(format.format(System.currentTimeMillis()));
                if (Now.getTime() - This.getTime() < 1000*3600*24*15){
                    assignmentInfos.add(assignmentInfo);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return assignmentInfos;

    }

    @Override
    public List<AssignmentInfoVO> findOrderByTime(int index) {
        List<AssignmentInfo> infoList = repository.findByAssignmentStatusOrderByCreateTime(AssignmentStatus.NEW.getCode());
        AssignmentInfoList2VOlistConverter converter = new AssignmentInfoList2VOlistConverter();
        List<AssignmentInfoVO> voList = converter.converter(infoList,accountService,detailService);
        while (voList.size()>index){
            voList.remove(index);
        }
        return voList;
    }

    @Override
    public Page<AssignmentInfo> findNoCriteria(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"id");
        return repository.findAll(pageable);
    }

    @Override
    public Page<AssignmentInfo> findCriteria(Integer page, Integer size, AssignmentQuery query) {
        Pageable pageable = new PageRequest(page,size, Sort.Direction.ASC,"id");
        Page<AssignmentInfo> assignmentInfoPage = repository.findAll(new Specification<AssignmentInfo>(){
            @Override
            public Predicate toPredicate(Root<AssignmentInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (null!=query.getName()&&!"".equals(query.getName())){
                    list.add(criteriaBuilder.equal(root.get("name").as(String.class),query.getName()));
                }
                if (null!=query.getIsbn()&&!"".equals(query.getIsbn())){
                    list.add(criteriaBuilder.equal(root.get("isbn").as(String.class),query.getIsbn()));
                }
                if (null!=query.getAuthor()&&!"".equals(query.getAuthor())){
                    list.add(criteriaBuilder.equal(root.get("author").as(String.class),query.getAuthor()));
                }
                Predicate[] p = new Predicate[list.size()];

                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return assignmentInfoPage;

    }

    @Override
    public Page<AssignmentInfoVO> findList(Integer categoryType, Pageable pageable) {
        Page<AssignmentInfo> infoPage = repository.findByAssignmentStatusAndCategoryType(AssignmentStatus.NEW.getCode(),
                categoryType,pageable);
        AssignmentInfoList2VOlistConverter converter = new AssignmentInfoList2VOlistConverter();
        List<AssignmentInfoVO> infoVOList = converter.converter(infoPage.getContent(),accountService,detailService);
        return new PageImpl<AssignmentInfoVO>(infoVOList,pageable,infoPage.getTotalElements());
    }

    @Override
    public Page<AssignmentInfoVO> findUserHistoryAssignmentO(String account, Pageable pageable) {
        Page<AssignmentInfo> infoPage = repository.findByAssignmentOwner(account, pageable);
        AssignmentInfoList2VOlistConverter converter = new AssignmentInfoList2VOlistConverter();
        List<AssignmentInfoVO> infoVOList = converter.converter(infoPage.getContent(),accountService,detailService);
        return new PageImpl<AssignmentInfoVO>(infoVOList,pageable,infoPage.getTotalElements());
    }

    @Override
    public Page<AssignmentInfoVO> findUserHistoryAssignmentR(String account, Pageable pageable) {
        Page<AssignmentInfo> infoPage = repository.findByAssignmentReceive(account,pageable);
        AssignmentInfoList2VOlistConverter converter = new AssignmentInfoList2VOlistConverter();
        List<AssignmentInfoVO> voList =  converter.converter(infoPage.getContent(),accountService,detailService);

        return new PageImpl<AssignmentInfoVO>(voList,pageable,infoPage.getTotalElements());
    }
}
