package com.xiaoyan.crowd.controller;

import com.aliyun.api.gateway.demo.util.CrowdShortMessageUtil;
import com.xiaoyan.crowd.myType.MessageForResponce;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalController {

    @RequestMapping("/")
    public String toPortalPage(){

//        MessageForResponce<String> tpl_0001 = CrowdShortMessageUtil.sendMessage("18970315142", "TPL_0001", "6");
        return "index";
    }

}
