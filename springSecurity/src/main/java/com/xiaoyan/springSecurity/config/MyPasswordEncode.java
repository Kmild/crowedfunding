package com.xiaoyan.springSecurity.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncode implements PasswordEncoder {

    /**
     * 加密
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {

        return null;
    }

    /**
     * 密码匹配
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }
}
