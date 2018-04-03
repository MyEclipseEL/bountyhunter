package com.example.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Administrator on 2018/4/1.
 */
@Entity
@Data
public class AssignmentCategory {


    @Id
    @GeneratedValue
    /** 类目id*/
    private Integer categoryId;

    /** 类目名字*/
    private String categoryName;

    /** 类目编号*/
    private Integer categoryType;


}
