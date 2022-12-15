package com.xiaoyan.crowd.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.xiaoyan.crowd.util.AccessPathResources;
import com.xiaoyan.crowd.util.MyConstantUtil;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CrowdAuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 判断当前请求是否要拦截
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {

        // 1.获取当前请求的路径
        String servletPath = RequestContext.getCurrentContext().
                                                     getRequest().getServletPath();
        // 2.判断该请求路径是否要拦截
        return
                !(
                     AccessPathResources.PATH_OK_SET.contains(servletPath)
                             ||
                     AccessPathResources.pathContainsStaticResource(servletPath)
                );
    }

    @Override
    public Object run() throws ZuulException {

         // 1.判断是否登录
         // 获取当前上下文
         RequestContext currentContext = RequestContext.getCurrentContext();

         // 获取request对象
         HttpServletRequest request = currentContext.getRequest();

         // 获取session判断当前用户是否登录
         HttpSession session = request.getSession();
         Object member = session.getAttribute(MyConstantUtil.ATTR_NAME_LOGIN_MEMBER);
         if(member == null){
             // 2.没登录则进行处理
                // 在session域中设置异常信息
                session.setAttribute(MyConstantUtil.ATTR_NAME_MESSAGE,MyConstantUtil.MESSAGE_NOT_LOGIN);

                 // 重定向到登录页面
                 HttpServletResponse response = currentContext.getResponse();
                 try {
                     response.sendRedirect("/auth/member/to/login");
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
         }

        // 登录了则放行
        return null;
    }
}
