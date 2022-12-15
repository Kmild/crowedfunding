package com.xiaoyan.crowd.mvc.controller;


import com.xiaoyan.crowd.myType.MessageForResponce;
import com.xiaoyan.crowd.mvc.pojo.Role;
import com.xiaoyan.crowd.service.AdminService;
import com.xiaoyan.crowd.service.AuthService;
import com.xiaoyan.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignController {

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthService authService;

    @RequestMapping("/assign/to/assign/role/page.html")
    public String processAdminRoleRelate(
            @RequestParam("adminId") Integer adminId,
            Model model
            ){

        // 查询数据
        List<Role> assignedRoles = roleService.getAssignedRole(adminId);
        List<Role> unAssignedRole = roleService.getUnAssignedRole(adminId);

        // 保存到request作用域
        model.addAttribute("assignedRole",assignedRoles);
        model.addAttribute("unAssignedRole",unAssignedRole);

        // 跳转页面
        return "relate-role-admin";
    }

    @RequestMapping("/assign/do/assign/role/page.html")
    public String processDoAssign(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String  keyword,
            //可以允许不传assignedRoleIdList这个参数
            @RequestParam(value = "assignedRoleIdList",required = false) List<Integer> assignedRoleIdList
    ){

        // 保存数据 : 1.去掉全部旧数据  2.保存新数据
        adminService.saveAssignRoleAdmin(adminId,assignedRoleIdList);

        return "redirect:/admin/do/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @ResponseBody
    @RequestMapping("/assign/do/role/auth.json")
    public MessageForResponce<String> processDoRoleAuth(@RequestBody Map<String,List<Integer>> map){

        // 保存数据
        roleService.saveAssignRoleAuth(map);

        //返回 处理的是ajax请求，返回json数据，此后继续执行后续js代码
        return MessageForResponce.ReturnMessageWithSuccess("ok",null);

    }


}
