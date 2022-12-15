import com.aliyun.api.gateway.demo.util.CrowdShortMessageUtil;
import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.xiaoyan.crowd.myType.MessageForResponce;
import com.xiaoyan.crowd.util.MessageUtilForRequestType;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class testUtil {

    @Test
    public void testPasswordUtil(){
        System.out.println(MessageUtilForRequestType.md5("12345"));

    }


    @Test
    public void testShortMessage(){
        // 随机验证码
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int number= (int) (Math.random()*10);
            code.append(number);
        }
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "5dd103ea5d42406391868f532b9f69a2";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+code);
        bodys.put("phone_number", "18370371145");
        bodys.put("template_id", "TPL_0000");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            System.out.println("response.getStatusLine() = " + response.getStatusLine());
            System.out.println("response.getEntity() = " + response.getEntity());
            System.out.println("response.getLocale() = " + response.getLocale());
            //获取response的body
             // response.getStatusLine() = HTTP/1.1 400 Bad Request
             //response.getStatusLine() = HTTP/1.1 200 OK
            System.out.println(EntityUtils.toString(response.getEntity()));

            String status = response.getStatusLine().toString();
            System.out.println("status.contains(\"200\") = " + status.contains("200"));
            System.out.println("status.substring = " + status.substring("HTTP/1.1 ".length()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testMyUtilForMessage(){
        MessageForResponce<String> tpl_0000 = CrowdShortMessageUtil.sendMessage("18970315142", "TPL_0001", null);
        System.out.println(tpl_0000);
    }

}
