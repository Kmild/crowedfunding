package com.xiaoyan.crowd.service.Impl;

import com.xiaoyan.crowd.mapper.AuthMapper;
import com.xiaoyan.crowd.mvc.pojo.Auth;
import com.xiaoyan.crowd.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(null);
    }

    @Override
    public List<Integer> getAuthByRoleId(Integer roleId) {
        return authMapper.selectByRoleId(roleId);
    }

    @Override
    public Auth getAuthByAuthId(Integer id) {
        return authMapper.selectByPrimaryKey(id);
    }
}
