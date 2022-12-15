package com.xiaoyan.crowd.mvc.config;

import com.google.gson.Gson;
import com.xiaoyan.crowd.mvc.pojo.CrowdUserDetailsService;
import com.xiaoyan.crowd.myType.MessageForResponce;
import com.xiaoyan.crowd.util.MessageUtilForRequestType;
import com.xiaoyan.crowd.util.MyConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 应用全局控制方法权限
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CrowdUserDetailsService crowdUserDetailsService;

    /**
     * 在 ioc 容器中装配BCryptPasswordEncoder类
     * @return
     */
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security
                .authorizeRequests()
                .antMatchers("/static/**","/admin/to/login.html")
                .permitAll()
                .antMatchers("/admin/do/page.html")
                .access("hasRole('经理') or hasAuthority('user:get')")
//                .antMatchers("/role/to/page.html")
//                .hasRole("部长")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()

                .and()
                .exceptionHandling()
                .accessDeniedHandler((HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
                    // 1.判断请求的类型
                    boolean isAjax = MessageUtilForRequestType.judgeRequestType(request);

                    // 2.如果不是ajax请求
                    if(!isAjax) {
                        request.setAttribute(MyConstantUtil.ATTR_NAME_EXCEPTION, new Exception(MyConstantUtil.MESSAGE_ACCESS_DENIED));
                        request.getRequestDispatcher("/WEB-INF/template/system-error.jsp").forward(request, response);
                    }else {
                    // 3.如果是ajax请求
                        //3.1创建通用的响应对象
                        MessageForResponce<Object> messageForResponce = MessageForResponce.ReturnMessageWithFail(accessDeniedException.getMessage());
                        //3.2将响应对象转换成json格式
                        Gson gson = new Gson();
                        String json = gson.toJson(messageForResponce);
                        //3.3将json格式的响应响应到浏览器
                        response.getWriter().write(json);
                    }
                }
                )

                .and()
                .csrf()
                .disable()


                .formLogin()
                .loginPage("/admin/to/login.html")  // 将此地址替换springSecurity默认的登录表单
//                /**
//                 * 指定后默认为：
//                 * /index.jsp GET         进入登录页地址
//                 * /index.jsp POST        处理的登录地址
//                 * /index.jsp?error GET   登录失败的地址
//                 * /index.jsp?logout GET  退出登录的地址
//                 */
                .loginProcessingUrl("/security/do/login.html")  // 表单提交的地址
//                .successForwardUrl("/admin/to/admin-main.html")  // 登录成功的地址
                .defaultSuccessUrl("/admin/to/admin-main.html")  // 登录成功的地址
//                .failureForwardUrl("/admin/to/login.html")     // 登录失败的地址
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutUrl("/admin/do/logout.html")  // 进入退出的地址
                .logoutSuccessUrl("/admin/to/login.html") // 退出成功的地址

;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 内存添加用户
//        auth.inMemoryAuthentication().withUser("tom").password("123123").roles("ADMIN");

        // 从数据库中查询
            auth.userDetailsService(crowdUserDetailsService).passwordEncoder(getBCryptPasswordEncoder());

    }
}
