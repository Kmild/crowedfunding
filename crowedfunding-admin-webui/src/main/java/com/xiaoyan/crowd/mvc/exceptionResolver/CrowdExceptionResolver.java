package com.xiaoyan.crowd.mvc.exceptionResolver;


import com.google.gson.Gson;
import com.xiaoyan.crowd.exception.CrowdAddDuplicateKeyException;
import com.xiaoyan.crowd.exception.CrowdForbiddenException;
import com.xiaoyan.crowd.exception.CrowdLoginFailedException;
import com.xiaoyan.crowd.exception.CrowdRemoveInvalidException;
import com.xiaoyan.crowd.myType.MessageForResponce;
import com.xiaoyan.crowd.util.MessageUtilForRequestType;
import com.xiaoyan.crowd.util.MyConstantUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice //表示当前类是一个基于注解的异常处理器类
public class CrowdExceptionResolver {


    /**
     * 处理登录失败的异常
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler({CrowdLoginFailedException.class, CrowdForbiddenException.class})
    //注意这里写了多个类 即此异常解析器会捕获两种异常，要注意方法中的参数能够接收这两种异常的参数
    public ModelAndView resolveLoginFailedException(RuntimeException exception,
                                                    HttpServletRequest request, HttpServletResponse response) {
        String viewName = "admin-login";
        ModelAndView modelAndView = null;

        try {
            //处理异常并返回modelAndView对象
            modelAndView = commonResolveException(viewName, exception, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modelAndView;
    }


    /**
     * 处理删除非法的异常
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(CrowdRemoveInvalidException.class)
    public ModelAndView resolveRemoveInvalidException(CrowdRemoveInvalidException exception,
                                                      HttpServletRequest request, HttpServletResponse response) {

        // 1. 获取请求地址所带的参数
        String requestURI = request.getRequestURI(); //  /admin/remove/{adminId}/{pageNum}/{keyword}"
        String[] split = requestURI.split("/");// requestURI = /crowedfunding/admin/remove/1/1/

        String pageNum = split[5].trim();  // pageNum
        String keyword = split.length >= 7 ? split[6].substring(0, split[6].length() - 5) : "";  // keyword

        // 2.拼接视图地址
        String viewName = "redirect:/admin/do/page.html?pageNum=" + pageNum + "&keyword=" + keyword;

        // 3.进行异常处理
        ModelAndView modelAndView = null;
        try {
            modelAndView = commonResolveException(viewName, exception, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 4.返回modelAndView对象
        return modelAndView;
    }


    /**
     * 处理添加数据时重复用户名的异常
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(CrowdAddDuplicateKeyException.class)
    public ModelAndView resolveDuplicateKeyException(CrowdAddDuplicateKeyException exception,
                                                     HttpServletRequest request, HttpServletResponse response) {
        // 判断将跳转的页面
        String viewName;
        String requestURI = request.getRequestURI();
        if (requestURI.contains("add")) {
            viewName = "admin-add";
        } else if (requestURI.contains("edit")) {
            viewName = "admin-edit";
        }else {
            viewName = "system-error";
        }

        ModelAndView modelAndView = null;

        try {
            //处理异常并返回modelAndView对象
            modelAndView = commonResolveException(viewName, exception, request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return modelAndView;
    }


    /**
     * 统一处理异常的工具方法
     *
     * @param viewName
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    private ModelAndView commonResolveException(String viewName, Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1.判断请求的类型
        boolean isAjax = MessageUtilForRequestType.judgeRequestType(request);

        //2.如果是ajax请求
        if (isAjax) {
            //2.1创建通用的响应对象
            MessageForResponce<Object> messageForResponce = MessageForResponce.ReturnMessageWithFail(exception.getMessage());
            //2.2将响应对象转换成json格式
            Gson gson = new Gson();
            String json = gson.toJson(messageForResponce);
            //2.3将json格式的响应响应到浏览器
            response.getWriter().write(json);
            //2.4 由于上面已经通过原生的responce对象返回了响应故不需要返回ModelAndView对象
            //注意：此时不会跳转页面！！！
            return null;
        }
        //3.如果不是ajax请求
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(MyConstantUtil.ATTR_NAME_EXCEPTION, exception);
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

}

