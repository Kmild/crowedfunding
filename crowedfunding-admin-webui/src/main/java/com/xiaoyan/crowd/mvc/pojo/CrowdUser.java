package com.xiaoyan.crowd.mvc.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CrowdUser extends User {
    private static final long serialVersionUID = 1L;

    // 原始的 Admin 对象，包含 Admin 对象的全部属性
    private Admin originalAdmin;

    public CrowdUser(
            // 传入原始的 Admin 对象
            Admin originalAdmin,
            // 创建角色、权限信息的集合
            List<GrantedAuthority> authorities
    ) {

        // 调用父类构造器
        super(originalAdmin.getUserName()  // 所以这里传入账号是什么意思? 经测试这个值对登录无影响
                , originalAdmin.getUserPsw(), authorities);
        // 给本类的 this.originalAdmin 赋值
        this.originalAdmin = originalAdmin;

        // 给本类的originalAdmin对象的密码进行擦除
        this.originalAdmin.setUserPsw(null);
    }

    // 对外提供的获取原始 Admin 对象的 getXxx()方法
    public Admin getOriginalAdmin() {
        return originalAdmin;
    }

}
