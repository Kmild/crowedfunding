package com.xiaoyan.crowd.mvc.controller;

import com.xiaoyan.crowd.myType.MessageForResponce;
import com.xiaoyan.crowd.mvc.pojo.Auth;
import com.xiaoyan.crowd.service.AdminService;
import com.xiaoyan.crowd.service.AuthService;
import com.xiaoyan.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;


    @ResponseBody
    @RequestMapping("/assign/get/all/auth.json")
    public MessageForResponce<List<Auth>> getAllAuth(){

        // 获取全部auth
        List<Auth> auths = authService.getAll();

        // 返回json数据
        return MessageForResponce.ReturnMessageWithSuccess("ok",auths);
    }

    @ResponseBody
    @RequestMapping("/assign/get/auth/by/roleId.json")
    public MessageForResponce<List<Integer>> getAuthByRoleId(@RequestParam("roleId") Integer roleId){

        // 根据roleId进行查询
        List<Integer> authByRoleId = authService.getAuthByRoleId(roleId);

        // 返回json数据
        return MessageForResponce.ReturnMessageWithSuccess("ok",authByRoleId);

    }


}
