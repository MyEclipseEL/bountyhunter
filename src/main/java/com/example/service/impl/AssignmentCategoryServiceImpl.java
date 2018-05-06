package com.example.service.impl;

import com.example.dataobject.AssignmentCategory;
import com.example.repository.CategoryRepository;
import com.example.service.AssignmentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */
@Service
public class AssignmentCategoryServiceImpl implements AssignmentCategoryService{

    @Autowired
    private CategoryRepository repository;

    @Override
    public AssignmentCategory findOne(Integer categoryId) {
        return repository.findOne(categoryId);
    }

    @Override
    public List<AssignmentCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public AssignmentCategory findByCategory(Integer categoryType) {
        return repository.findByCategoryType(categoryType);
    }

    @Override
    public List<AssignmentCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public AssignmentCategory save(AssignmentCategory category) {
        return repository.save(category);
    }

}
