package com.xiaoyan.crowd.api;

import com.xiaoyan.crowd.mvc.pojo.MemberPO;
import com.xiaoyan.crowd.mvc.vo.MemberVO;

public interface MemberService {
    MemberPO getMemberPOByLoginAcc(String loginAcc);

    void saveMember(MemberPO memberPO);
}
