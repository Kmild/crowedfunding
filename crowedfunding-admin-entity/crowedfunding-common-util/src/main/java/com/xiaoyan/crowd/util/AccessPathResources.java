package com.xiaoyan.crowd.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 不需要权限检查
 */
public class AccessPathResources {

    public static final Set<String> PATH_OK_SET = new HashSet<>();

    static {
        PATH_OK_SET.add("/");
        PATH_OK_SET.add("/auth/member/to/register/page");
        PATH_OK_SET.add("/auth/member/to/login");
        PATH_OK_SET.add("/auth/member/do/login");
        PATH_OK_SET.add("/auth/member/do/logout");
        PATH_OK_SET.add("/auth/do/member/register");
        PATH_OK_SET.add("/auth/member/send/short/message");
    }

    public static final Set<String> STATIC_OK_SET = new HashSet<>();

    static {
        STATIC_OK_SET.add("bootstrap");
        STATIC_OK_SET.add("css");
        STATIC_OK_SET.add("fonts");
        STATIC_OK_SET.add("img");
        STATIC_OK_SET.add("jquery");
        STATIC_OK_SET.add("layer");
        STATIC_OK_SET.add("script");
        STATIC_OK_SET.add("ztree");
    }

    /**
     * 判断路径是否是访问静态资源
     * @param servletPath
     * @return
     *  true 是
     *  false 否
     */
    public static boolean pathContainsStaticResource(String servletPath){

        // 判断字符串是否有效
        if(servletPath == null || servletPath.length() == 0){
            throw new RuntimeException(MyConstantUtil.MESSAGE_STRING_INVALIDATE);
        }

        // 得到路径的一级目录
        String[] split = servletPath.split("/");
        String firstLevelPath = split[1];

        return STATIC_OK_SET.contains(firstLevelPath);
    }


}
