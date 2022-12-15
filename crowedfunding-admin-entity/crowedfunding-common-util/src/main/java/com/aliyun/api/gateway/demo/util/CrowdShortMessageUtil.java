package com.aliyun.api.gateway.demo.util;

import com.xiaoyan.crowd.myType.MessageForResponce;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class CrowdShortMessageUtil {

   private static final String host = "https://dfsns.market.alicloudapi.com";
   private static final String path = "/data/send_sms";
   private static final String method = "POST";
   private static final String appcode = "5dd103ea5d42406391868f532b9f69a2";


    /**
     * 成功返回 code(短信验证码)，失败返回错误信息
     * @param phoneNumber
     * @param template
     * @return
     */
    public static MessageForResponce<String> sendMessage(
            String phoneNumber,  // 发送短信的目标电话号码
            String template ,    // 签名
            String availTime       // 有效时间
    ){
        // 随机验证码
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int number= (int) (Math.random()*10);
            code.append(number);
        }

        if(template == null || template == ""){
            template = "TPL_0001";
        }

        if(availTime == null || availTime == ""){
           availTime="5";
        }
        availTime= ",expire_at:"+availTime;

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+code+availTime);
        bodys.put("phone_number", phoneNumber);
        bodys.put("template_id", template);


        try {

            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());

            // 判断响应状态
            String status = response.getStatusLine().toString();
            if(status.contains("200")){
                return MessageForResponce.ReturnMessageWithSuccess("ok",code.toString());
            }else {
                return MessageForResponce.ReturnMessageWithFail(EntityUtils.toString(response.getEntity()));
            }
        } catch (Exception e) {
          e.printStackTrace();
          return MessageForResponce.ReturnMessageWithFail(e.getMessage());
        }
    }


}
