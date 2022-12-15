package com.xiaoyan.crowd.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String loginAcct;

    private String userPswd;

    private String userName;

    private String email;

    private String phoneNum;

    private String code;


}
