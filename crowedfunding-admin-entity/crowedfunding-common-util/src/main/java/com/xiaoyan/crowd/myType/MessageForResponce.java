package com.xiaoyan.crowd.myType;

import com.xiaoyan.crowd.util.MyConstantUtil;

/**
 * 封装统一响应信息
 * @param <T>
 */
public class MessageForResponce<T> {


    private String statue;
    private T data;
    private String message;

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static <K> MessageForResponce<K> ReturnMessageWithSuccess(String message, K data){
        return new MessageForResponce<K>(MyConstantUtil.ATTR_NAME_SUCCESS,data,message);
    }

    public static <K> MessageForResponce<K> ReturnMessageWithFail(String message){
        return new MessageForResponce<K>(MyConstantUtil.ATTR_NAME_FAIL,null,message);
    }

    private MessageForResponce(String statue, T data, String message) {
        this.statue = statue;
        this.data = data;
        this.message = message;
    }

    public MessageForResponce() {
    }

    @Override
    public String toString() {
        return "MessageForResponce{" +
                "statue='" + statue + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
