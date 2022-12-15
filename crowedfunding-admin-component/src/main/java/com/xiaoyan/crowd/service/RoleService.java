package com.xiaoyan.crowd.service;

import com.github.pagehelper.PageInfo;
import com.xiaoyan.crowd.mvc.pojo.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    //根据关键字，页码等信息查询分页信息
    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRoles(List<Integer> roles);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);

    void saveAssignRoleAuth(Map<String, List<Integer>> map);
}
