package com.xiaoyan.crowd.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageUtilForRequestType {


    /*
     * 判断当前请求是否为 Ajax
     * 请求
     * @param request
     *  @return
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1.获取请求消息头信息
        String acceptInformation = request.getHeader("Accept");
        String xRequestInformation = request.getHeader("X-Requested-With");

        // 2.检查并返回
        return (
                acceptInformation != null
                        && acceptInformation.length() > 0
                        && acceptInformation.contains("application/json"))
                ||
                (
                 xRequestInformation != null
                                && xRequestInformation.length() > 0
                                && xRequestInformation.equals("XMLHttpRequest"));
    }


    /**
     * md5加密
     * @param source
     * @return
     */
    public static String md5(String source){

            // 1.判断source是否有效
        if(source == null || source.length() == 0){
            //2.如果source无效就return null
           return null;
        }


        try {
            // 3.获取MessageDigest对象
            String algorithm="md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            //4.获取明文字符串对应的字节数组
            byte[] input = source.getBytes();

            //5.执行加密
            byte[] output = messageDigest.digest(input);

            //6.创建BigInteger对象
            int signum=1;
            BigInteger bigInteger = new BigInteger(signum, output);

            //7.按照16进制将bigInteger的值转换成字符串
            int radix=16;
            String encode = bigInteger.toString(radix).toUpperCase();

            return encode;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }






}
