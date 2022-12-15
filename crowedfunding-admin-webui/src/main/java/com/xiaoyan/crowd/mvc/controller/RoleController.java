package com.xiaoyan.crowd.mvc.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoyan.crowd.myType.MessageForResponce;
import com.xiaoyan.crowd.mvc.pojo.Role;
import com.xiaoyan.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PreAuthorize("hasRole('部长')")
    @RequestMapping("/role/get/page/info.json")
    public MessageForResponce<PageInfo<Role>> doPage(
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
            @RequestParam(value = "keyword",defaultValue = "")String keyword
    ){

        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);


        return MessageForResponce.ReturnMessageWithSuccess(null,pageInfo);
    }


    @RequestMapping("/role/do/add.json")
    public MessageForResponce<String> saveRole(Role role){
        roleService.saveRole(role);

        return MessageForResponce.ReturnMessageWithSuccess("ok",null);
    }


    @RequestMapping("/role/do/eidt.json")
    public MessageForResponce<String> updateRole(Role role){
        roleService.updateRole(role);

        return MessageForResponce.ReturnMessageWithSuccess("ok",null);
    }


    @RequestMapping("/role/do/remove.json")
    public MessageForResponce<String> removeRole(@RequestBody List<Integer> roles){ //记得要用@RequestBody注解
        roleService.removeRoles(roles);

        return MessageForResponce.ReturnMessageWithSuccess("ok",null);
    }

}
