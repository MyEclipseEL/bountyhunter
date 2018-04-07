package com.example.VO;

import com.example.dataobject.UserDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/4/4.
 */
@Data
public class UserAccountVO {

    @JsonProperty("account")
    private String accountId;

    @JsonProperty("name")
    private String userName;

    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("detail")
    private UserDetail userDetail;

    @JsonIgnore
    private String userPassword;

    @JsonIgnore
    private String detailId;


}
