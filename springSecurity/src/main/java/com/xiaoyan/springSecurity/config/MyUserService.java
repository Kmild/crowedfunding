package com.xiaoyan.springSecurity.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(
            String username  //表单提交的用户名
    ) throws UsernameNotFoundException {

         //装配数据...

        // 装配角色和权限
        List<GrantedAuthority> authorityList = new ArrayList<>();
        String role1 = "ADMIN";
        String auth1 = "UPDATE";

        // 注意装配角色时需要 添加前缀 "ROLE_"
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+role1));
        authorityList.add(new SimpleGrantedAuthority(auth1));

        String psw ="123123";

        return new User(username,psw,authorityList);
    }
}
