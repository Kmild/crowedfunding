package com.xiaoyan.crowd.api;

import com.xiaoyan.crowd.mvc.vo.MemberVO;
import com.xiaoyan.crowd.myType.MessageForResponce;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

@FeignClient("atxiaoyan-crowd-redis")
public interface RedisRemoteService {

    @RequestMapping("/set/redis/key/value/remote")
    MessageForResponce<String> setRedisKeyValueRemote(
            @RequestParam("key") String key, @RequestParam("value") String value);

    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    MessageForResponce<String> setRedisKeyValueRemoteWithTimeout(
            @RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("time") long time, @RequestParam("timeUnix") TimeUnit timeUnit);

    @RequestMapping("/get/redis/string/value/by/key")
    MessageForResponce<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key);

    @RequestMapping("/remove/redis/key/remote")
    MessageForResponce<String> removeRedisKeyRemote(@RequestParam("key") String key);



}
