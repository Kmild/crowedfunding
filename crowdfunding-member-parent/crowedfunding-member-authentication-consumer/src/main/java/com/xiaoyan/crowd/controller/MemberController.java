package com.xiaoyan.crowd.controller;

import com.aliyun.api.gateway.demo.util.CrowdShortMessageUtil;
import com.xiaoyan.crowd.api.MySQLRemoteService;
import com.xiaoyan.crowd.api.RedisRemoteService;
import com.xiaoyan.crowd.mvc.pojo.MemberPO;
import com.xiaoyan.crowd.mvc.vo.MemberLoginVO;
import com.xiaoyan.crowd.mvc.vo.MemberVO;
import com.xiaoyan.crowd.myType.MessageForResponce;
import com.xiaoyan.crowd.util.MyConstantUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberController {

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @ResponseBody
    @RequestMapping("/auth/member/send/short/message")
    public MessageForResponce<String> sendMessage(@RequestParam("phoneNum")String phoneNum){

        // 1.发送验证码到phoneNum
        MessageForResponce<String> result = CrowdShortMessageUtil.sendMessage(phoneNum, null, null);
        // 2.判断短信发送的结果
        if(result.getStatue() == "success"){
            // 3.成功则将验证码存入redis
            String key = MyConstantUtil.REDIS_CODE_PREFIX+ phoneNum;
            String code = result.getData();

            MessageForResponce<String> resRedis =
                    redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 10, TimeUnit.MINUTES);
            // 4.判断保存是否成功
            if("success".equals(resRedis.getStatue())){
                return MessageForResponce.ReturnMessageWithSuccess("ok",null);
            }
            // 失败则返回失败信息
            return resRedis;
        }
        // 失败则返回失败信息
        return result;
    }


    @RequestMapping("/auth/do/member/register")
    String checkCode(MemberVO memberVO, Model model){

        // 1.获取用户输入的手机号、
        String phoneNum = memberVO.getPhoneNum();

        // 2.拼redis验证码中的key
        String key = MyConstantUtil.REDIS_CODE_PREFIX+phoneNum;

        // 3.从redis中读取key对应的value
        MessageForResponce<String> codeRedis = redisRemoteService.getRedisStringValueByKeyRemote(key);

        // 4.检查读取操作是否成功
        if("success".equals(codeRedis.getStatue())){
            // 5.成功则校验验证码
            String codeForm = memberVO.getCode();
            String codeRedisData = codeRedis.getData();
            if(Objects.equals(codeForm,codeRedisData)){
                // 6.校验成功 删除code
                MessageForResponce<String> removeRedisKeyRemote = redisRemoteService.removeRedisKeyRemote(key);
                // .....此处省略判断
                // 7.执行密码加密
                String userPswdBefore = memberVO.getUserPswd();
                String userPswdAfter = new BCryptPasswordEncoder().encode(userPswdBefore);

                memberVO.setUserPswd(userPswdAfter);

                // 8.将数据保存到数据库
                MemberPO memberPO = new MemberPO();
                BeanUtils.copyProperties(memberVO,memberPO); // 复制VO的属性到PO

                MessageForResponce<String> saveMember = mySQLRemoteService.saveMember(memberPO);

                // 9.判断保存是否成功
                if("success".equals(saveMember.getStatue())){
                    return "redirect:http://localhost:80/auth/member/to/login";
                }else {
                 model.addAttribute(MyConstantUtil.ATTR_NAME_MESSAGE,saveMember.getMessage());
                }

            }else {
                model.addAttribute(MyConstantUtil.ATTR_NAME_MESSAGE, MyConstantUtil.MESSAGE_CODE_NOT_CORRECT);
            }
        }else {
            model.addAttribute(MyConstantUtil.ATTR_NAME_MESSAGE, MyConstantUtil.MESSAGE_SYSTEM_ERROR_REDIS_GET_FAILED);
        }
        return "member-register";
    }


    @RequestMapping("/auth/member/do/login")
    public String doLogin(@RequestParam("loginAcct")String loginAcct, @RequestParam("userPswd")String userPswd,
                          Model model, HttpSession session){

        // 1.根据账号获取用户信息
        MessageForResponce<MemberPO> remoteByLoginAcct = mySQLRemoteService.getRemoteByLoginAcct(loginAcct);

        // 2.判断信息是否获取成功
        if("success".equals(remoteByLoginAcct.getStatue())){
            // 3.校验密码
            MemberPO memberPO = remoteByLoginAcct.getData();
            String pswDB = memberPO.getUserPswd();
            boolean matches = new BCryptPasswordEncoder().matches(userPswd, pswDB);

            if(!matches){
                // 校验失败则返回登录页面
                model.addAttribute(MyConstantUtil.ATTR_NAME_MESSAGE,MyConstantUtil.MESSAGE_LOGIN_FAILED);
                return "member-login";
            }

            // 4.创建MemberLoginVO将登录信息保存到session共享域
            MemberLoginVO memberLoginVO =
                    new MemberLoginVO(memberPO.getId(),memberPO.getUserName(),memberPO.getEmail());
            // 将当前用户登录信息保存到session作用域
            session.setAttribute(MyConstantUtil.ATTR_NAME_LOGIN_MEMBER,memberLoginVO);

            // 5.重定向到member主页面
            return "redirect:http://localhost:80/auth/member/to/center";
        }
        model.addAttribute(MyConstantUtil.ATTR_NAME_MESSAGE,remoteByLoginAcct.getMessage());
        return "member-login";

    }


    @RequestMapping("/auth/member/do/logout")
    public String doLogout(HttpSession session){
        // 清空session域中的登录信息
        session.invalidate();

        return "redirect:http://localhost:80/";
    }







}
