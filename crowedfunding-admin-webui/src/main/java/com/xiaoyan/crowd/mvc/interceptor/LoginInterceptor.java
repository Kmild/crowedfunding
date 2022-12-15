package com.xiaoyan.crowd.mvc.interceptor;

import com.xiaoyan.crowd.exception.CrowdForbiddenException;
import com.xiaoyan.crowd.mvc.pojo.Admin;
import com.xiaoyan.crowd.util.MyConstantUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1.获取session对象
        HttpSession session = request.getSession();

        // 2.尝试从session中获取admin对象
        Admin admin =(Admin) session.getAttribute(MyConstantUtil.ATTR_NAME_LOGIN_ADMIN);

        // 3.判断admin对象是否为空-->该用户是否登录
        if(admin == null){
            throw new CrowdForbiddenException(MyConstantUtil.MESSAGE_NOT_LOGIN);
        }

        // 4.不为空则放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
