package com.xiaoyan.crowd.controller;

import com.xiaoyan.crowd.mvc.vo.MemberVO;
import com.xiaoyan.crowd.myType.MessageForResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/set/redis/key/value/remote")
    MessageForResponce<String> setRedisKeyValueRemote(
            @RequestParam("key") String key, @RequestParam("value") String value){
        try {
            ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
            opsForValue.set(key, value);

            return MessageForResponce.ReturnMessageWithSuccess("ok",null);
        }catch (Exception e){
            e.printStackTrace();

            return MessageForResponce.ReturnMessageWithFail(e.getMessage());
        }
    }

    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    MessageForResponce<String> setRedisKeyValueRemoteWithTimeout(
            @RequestParam("key") String key, @RequestParam("value") String value,
            @RequestParam("time") long time, @RequestParam("timeUnix") TimeUnit timeUnit){

        try{
            ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
            opsForValue.set(key,value,time,timeUnit);

            return MessageForResponce.ReturnMessageWithSuccess("ok",null);
        }catch (Exception e){
            e.printStackTrace();
            return MessageForResponce.ReturnMessageWithFail(e.getMessage());
        }
    }


    @RequestMapping("/get/redis/string/value/by/key")
    MessageForResponce<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key){
        try{
            ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
            String value = opsForValue.get(key);

            return MessageForResponce.ReturnMessageWithSuccess("ok",value);
        }catch (Exception e){
            e.printStackTrace();
            return MessageForResponce.ReturnMessageWithFail(e.getMessage());
        }

    }

    @RequestMapping("/remove/redis/key/remote")
    MessageForResponce<String> removeRedisKeyRemote(@RequestParam("key") String key){

        try{

            redisTemplate.delete(key);

            return MessageForResponce.ReturnMessageWithSuccess("ok",null);
        }catch (Exception e){
            e.printStackTrace();
            return MessageForResponce.ReturnMessageWithFail(e.getMessage());
        }
    }





}
