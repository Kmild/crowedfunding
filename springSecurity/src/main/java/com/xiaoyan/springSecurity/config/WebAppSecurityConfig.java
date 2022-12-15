package com.xiaoyan.springSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity  // 启用web安全功能
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserService userService;

    /**
     * 设置资源
     * @param security
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {

//        security
//                .authorizeRequests() // 对请求进行授权
//                .antMatchers("/index.jsp") // 针对/index.jsp路径进行授权
//                .permitAll()   // 可以无条件访问
//                .antMatchers("/layui/**")
//                .permitAll()
//                .and()
//                .authorizeRequests()
//                .anyRequest()  // 对任意请求进行授权
//                .authenticated()  // 需要登录后才能访问
//                .and()
//                .formLogin()  // 以表单方式登录
//                .loginPage("/index.jsp") // 指定登录页面
//                .permitAll()
//                /**
//                 * 指定后：
//                 * /index.jsp GET - the login form   进入登录页地址
//                 * /index.jsp POST - process the credentials and if valid authenticate the user  处理登录地址
//                 * /index.jsp?error GET - redirect here for failed authentication attempts  登录失败的地址
//                 * /index.jsp?logout GET - redirect here after successfully logging out 退出登录的地址
//                 */
//                .loginProcessingUrl("/do/login.html") // 指定提交登录表单的地址
//                .permitAll()
//                .usernameParameter("loginName")  // 指定登录账号参数名
//                .passwordParameter("loginPsw")   // 指定登录密码参数名
//                .successForwardUrl("/main.html")  // 指定登录成功跳转的地址
//                .permitAll()
//                .failureForwardUrl("/index.jsp")  // 指定登陆失败跳转的地址
//                .permitAll();

        security.authorizeRequests() //对请求进行授权
                .antMatchers("/layui/**","/index.jsp") //使用 ANT 风格设置要授权的 URL 地 址
                .permitAll() //允许上面使用 ANT 风格设置的全部请求
                .antMatchers("/level1/**")
                .hasRole("学徒")
                .anyRequest() //其他未设置的全部请求
                .authenticated() //需要认证
                .and()
                .formLogin() //设置未授权请求跳转到登录页面：开启表单登 录功能
                .loginPage("/index.jsp") //指定登录页
                .permitAll() //为登录页设置所有人都可以访问
                .loginProcessingUrl("/do/login.html") // 指定提交登录表单的地址
                .usernameParameter("loginName") // 定制登录账号的请求参数名
                .passwordParameter("loginPsw") // 定制登录密码的请求参数名
                .defaultSuccessUrl("/main.html") //设置登录成功后默认前往的 URL 地址
                .and()
                .logout()
                .logoutUrl("/do/logout.html") // 定制退出登录的地址
                .logoutSuccessUrl("/index.jsp")
                .and()
                .exceptionHandling()  // 指定异常处理器
                .accessDeniedPage("/to/no/auth/page.html")  // 指定异常处理后要去的页面

        ;



    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

//        builder.inMemoryAuthentication()
//                .withUser("tom").password("123123") //设置账号密码
//                .roles("ADMIN","学徒") //设置角色
//                .and()
//                .withUser("jerry").password("456456")//设置另一个账号密码
//                .authorities("SAVE","EDIT"); //设置权限

        builder.userDetailsService(userService);
    }
}
