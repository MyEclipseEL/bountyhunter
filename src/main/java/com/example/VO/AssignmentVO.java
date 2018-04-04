package com.example.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
@Data
public class AssignmentVO {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("assignments")
    private List<AssignmentInfoVO> assignmentInfoVOList;
}
