package com.xiaoyan.crowd.service;

import com.github.pagehelper.PageInfo;
import com.xiaoyan.crowd.mvc.pojo.Admin;

import java.util.List;

public interface AdminService {

    //添加单个管理员
    void saveAdmin(Admin admin);

    //查询所有管理员所有信息
    List<Admin> getAll();

    Admin checkLogin(String username, String password);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void removeById(Integer adminId);

    Admin getAdminById(Integer adminId);

    void updateAdminByPrimaryKrySelective(Admin admin);

    void saveAssignRoleAdmin(Integer adminId, List<Integer> assignedRoleIdList);

    Admin getAdminByName(String logAcc);
}
