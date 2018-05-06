package com.example.service;

import com.example.dataobject.AssignmentCategory;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */
public interface AssignmentCategoryService {

    AssignmentCategory findOne(Integer categoryId);

    List<AssignmentCategory> findAll();

    AssignmentCategory findByCategory(Integer categoryType);

    /** 通过集合查找*/
    List<AssignmentCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /** 新增或更新*/
    AssignmentCategory save(AssignmentCategory category);

}
