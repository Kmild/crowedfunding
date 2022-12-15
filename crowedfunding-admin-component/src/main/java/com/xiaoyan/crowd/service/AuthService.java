package com.xiaoyan.crowd.service;

import com.xiaoyan.crowd.mvc.pojo.Auth;

import java.util.List;

public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAuthByRoleId(Integer roleId);

    Auth getAuthByAuthId(Integer id);
}
