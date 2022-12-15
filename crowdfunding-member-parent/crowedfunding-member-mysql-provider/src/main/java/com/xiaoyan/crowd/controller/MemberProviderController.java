package com.xiaoyan.crowd.controller;

import com.xiaoyan.crowd.api.MemberService;
import com.xiaoyan.crowd.mvc.pojo.MemberPO;
import com.xiaoyan.crowd.myType.MessageForResponce;
import com.xiaoyan.crowd.util.MyConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberProviderController {

    @Autowired
    private MemberService memberService;


    @RequestMapping("/get/remote/by/loginacc")
    MessageForResponce<MemberPO> getRemoteByLoginAcct(@RequestParam("loginAcc")String loginAcc){

        try {
            MemberPO memberPOByLoginAcc = memberService.getMemberPOByLoginAcc(loginAcc);

            if (memberPOByLoginAcc == null) {
                return MessageForResponce.ReturnMessageWithFail("用户名不存在！");
            }

            return MessageForResponce.ReturnMessageWithSuccess("ok" + loginAcc, memberPOByLoginAcc);
        }catch (Exception e){
            e.printStackTrace();
            return MessageForResponce.ReturnMessageWithFail(e.getCause().getMessage());
        }
    }

    @RequestMapping("/save/member/remote")
    MessageForResponce<String> saveMember(@RequestBody MemberPO memberPO){

        try {
            memberService.saveMember(memberPO);

            return MessageForResponce.ReturnMessageWithSuccess("ok",null);
        }catch (DuplicateKeyException e){
            return MessageForResponce.ReturnMessageWithFail(MyConstantUtil.MESSAGE_ADD_DUPLICATEKRY);
        }
        catch (Exception e){
            e.printStackTrace();
            return MessageForResponce.ReturnMessageWithFail(e.getCause().getMessage());
        }
    }
}
