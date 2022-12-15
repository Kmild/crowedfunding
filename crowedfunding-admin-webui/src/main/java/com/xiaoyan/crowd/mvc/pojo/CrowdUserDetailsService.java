package com.xiaoyan.crowd.mvc.pojo;

import com.xiaoyan.crowd.service.AdminService;
import com.xiaoyan.crowd.service.AuthService;
import com.xiaoyan.crowd.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1.根据传进来的username(-->对应数据库中的login_acct)查询该用户信息
        Admin admin = adminService.getAdminByName(username);

        // 2.根据用户信息查询该用户拥有的角色和角色拥有的权限
            // 2.1查询用户拥有的角色
            List<Role> roleList = roleService.getAssignedRole(admin.getId());

            // 2.2查询角色拥有的权限
            List<Auth> authList = new ArrayList<>();
            for (Role role : roleList) {
                    // 获取单个角色拥有的权限的id
                    List<Integer> authIdList = authService.getAuthByRoleId(role.getId());

                    // 根据权限id查询权限
                    for (Integer id : authIdList) {
                        Auth auth = authService.getAuthByAuthId(id);
                        // 去重处理
                        if(!authList.contains(auth)){
                            authList.add(auth);
                        }
                    }
            }

        // 3.创建权限列表存入角色和权限信息
        List<GrantedAuthority> authorityList = new ArrayList<>();
            // 存入角色信息
            for (Role role : roleList) {
                String roleName = "ROLE_" + role.getName();
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
                authorityList.add(simpleGrantedAuthority);
            }

            // 存入权限信息
            for (Auth auth : authList) {
                String authName = auth.getName();
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
                authorityList.add(simpleGrantedAuthority);
            }

        // 4.创建用户对象并返回

        CrowdUser crowdUser = new CrowdUser(admin, authorityList);

        return crowdUser;
    }
}
