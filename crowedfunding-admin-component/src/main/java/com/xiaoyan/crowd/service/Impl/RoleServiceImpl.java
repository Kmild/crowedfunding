package com.xiaoyan.crowd.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoyan.crowd.mapper.RoleMapper;
import com.xiaoyan.crowd.mvc.pojo.Role;
import com.xiaoyan.crowd.mvc.pojo.RoleExample;
import com.xiaoyan.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {

        // 1.开启分页
        PageHelper.startPage(pageNum,pageSize);

        // 2.根据关键字查询
           // 2.1 组装查询条件
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andNameLike("%"+keyword+"%");
          // 2.2 进行查询
        List<Role> roles = roleMapper.selectByExample(roleExample);

        // 3.将查询结果封装到pageInfo中
        return new PageInfo<>(roles);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRoles(List<Integer> roles) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andIdIn(roles);

        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.selectAssignedRoles(adminId);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer adminId) {
        return roleMapper.selectUnAssignedRoles(adminId);
    }

    @Override
    public void saveAssignRoleAuth(Map<String, List<Integer>> map) {

        // 1.取出数据
            // 取出 roleId
            List<Integer> roleIdList = map.get("roleId");
            Integer roleId = roleIdList.get(0);
            // 取出 auth 的 id集合
            List<Integer> authIds = map.get("authIds");

        // 2.删除所有旧数据
        roleMapper.deleteAllOldRelatinshipById(roleId);

        // 3.添加新数据
          // 如果新数据存在则添加
          if(authIds != null && authIds.size() > 0){
              roleMapper.insertAllNewRelationshipById(roleId,authIds);
          }
          // 否则不添加
    }
}
