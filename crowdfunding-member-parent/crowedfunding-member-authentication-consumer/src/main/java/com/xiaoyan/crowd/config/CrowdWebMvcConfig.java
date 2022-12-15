package com.xiaoyan.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig  implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // 浏览器访问的地址 --> 去往注册页
        String urlPathRegister = "/auth/member/to/register/page";
        // 视图名称
        String viewNameRegister = "member-register";

        registry.addViewController(urlPathRegister).setViewName(viewNameRegister);

        // 浏览器访问的地址 --> 去往登陆页
        String urlPathLogin = "/auth/member/to/login";
        // 视图名称
        String viewNameLogin = "member-login";

        registry.addViewController(urlPathLogin).setViewName(viewNameLogin);

        // 浏览器访问的地址 --> 去往member主页面
        String urlPathCenter = "/auth/member/to/center";
        // 视图名称
        String viewNameCenter = "member-center";

        registry.addViewController(urlPathCenter).setViewName(viewNameCenter);


        registry.addViewController("member/to/crowd").setViewName("member-crowd");
        registry.addViewController("member/to/start").setViewName("member-start");

    }
}
