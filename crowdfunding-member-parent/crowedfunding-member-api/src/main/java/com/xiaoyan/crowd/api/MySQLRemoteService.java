package com.xiaoyan.crowd.api;

import com.xiaoyan.crowd.mvc.pojo.MemberPO;
import com.xiaoyan.crowd.mvc.vo.MemberVO;
import com.xiaoyan.crowd.myType.MessageForResponce;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("atxiaoyan-crowd-mysql")
public interface MySQLRemoteService {

    // 根据账号查询用户信息
    @RequestMapping("/get/remote/by/loginacc")
    MessageForResponce<MemberPO> getRemoteByLoginAcct(@RequestParam("loginAcc")String loginAcc);

    // 保存用户
    @RequestMapping("/save/member/remote")
    MessageForResponce<String> saveMember(@RequestBody MemberPO memberPO);


}
