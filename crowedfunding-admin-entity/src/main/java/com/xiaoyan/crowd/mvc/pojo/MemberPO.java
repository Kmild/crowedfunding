package com.xiaoyan.crowd.mvc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPO {
    private Integer id;

    private String loginAcct;

    private String userPswd;

    private String userName;

    private String email;

    private Integer authStatus;

    private Integer userType;

    private String realName;

    private String cardNum;

    private Integer acctType;

    private String phoneNum;

}