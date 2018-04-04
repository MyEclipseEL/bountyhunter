package com.example.repository;

import com.example.dataobject.AssignmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */
public interface CategoryRepository extends JpaRepository<AssignmentCategory,Integer> {

    List<AssignmentCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
