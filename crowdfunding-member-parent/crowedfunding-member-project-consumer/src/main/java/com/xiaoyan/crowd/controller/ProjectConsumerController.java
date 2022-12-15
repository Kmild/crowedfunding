package com.xiaoyan.crowd.controller;

import com.xiaoyan.crowd.config.OSSProperties;
import com.xiaoyan.crowd.mvc.vo.ProjectVO;
import com.xiaoyan.crowd.myType.MessageForResponce;
import com.xiaoyan.crowd.util.CrowdFileUploadUtil;
import com.xiaoyan.crowd.util.MyConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectConsumerController {

    @Autowired
    private OSSProperties ossProperties;

    @RequestMapping("/member/do/crowd/step1")
    public String saveBasicInfo(
            ProjectVO projectVO,
            MultipartFile headerPicture,
            List<MultipartFile> detailPicturePath,
            HttpSession session,
            ModelMap modelMap
    ) throws IOException {

        // 一、完成头图上传
        // 1.获取当前 headerPicture 对象是否为空
        boolean headerPictureIsEmpty = headerPicture.isEmpty();
        if (headerPictureIsEmpty) {
            // 2.如果没有上传头图则返回到表单页面并显示错误消息
            modelMap.addAttribute(MyConstantUtil.ATTR_NAME_MESSAGE, MyConstantUtil.MESSAGE_HEADER_PIC_EMPTY);
            return "start-step-1";
        }
        // 3.如果用户确实上传了有内容的文件，则执行上传
        MessageForResponce<String> uploadHeaderPicResultEntity = CrowdFileUploadUtil.uploadFileToOSS(
                ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), headerPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), headerPicture.getOriginalFilename());
        String result = uploadHeaderPicResultEntity.getStatue();
        // 4.判断头图是否上传成功
        if ("success".equals(result)) {
            // 5.如果成功则从返回的数据中获取图片访问路径
            String headerPicturePath = uploadHeaderPicResultEntity.getData();
            // 6.存入 ProjectVO 对象中
            projectVO.setHeaderPicturePath(headerPicturePath);
        } else {
            // 7.如果上传失败则返回到表单页面并显示错误消息
            modelMap.addAttribute(MyConstantUtil.ATTR_NAME_MESSAGE, MyConstantUtil.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "start-step-1";
        }
        // 二、上传详情图片
        // 1.创建一个用来存放详情图片路径的集合
        List<String> detailPicturePathList = new ArrayList<String>();
        // 2.检查 detailPictureList 是否有效
        if (detailPicturePath == null || detailPicturePath.size() == 0) {
            modelMap.addAttribute(MyConstantUtil.ATTR_NAME_MESSAGE, MyConstantUtil.MESSAGE_DETAIL_PIC_EMPTY);
            return "start-step-1";
        }
        // 3.遍历 detailPictureList 集合
        for (MultipartFile detailPicture : detailPicturePath) {
            // 4.当前 detailPicture 是否为空
            if (detailPicture.isEmpty()) {
                // 5.检测到详情图片中单个文件为空也是回去显示错误消息
                modelMap.addAttribute(MyConstantUtil.ATTR_NAME_MESSAGE, MyConstantUtil.MESSAGE_DETAIL_PIC_EMPTY);
                return "start-step-1";
            }
            // 6.执行上传
            MessageForResponce<String> detailUploadResultEntity = CrowdFileUploadUtil.uploadFileToOSS(
                    ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), detailPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), detailPicture.getOriginalFilename());
            // 7.检查上传结果
            String detailUploadResult = detailUploadResultEntity.getStatue();
            if ("success".equals(detailUploadResult)) {
                String picturePath = detailUploadResultEntity.getData();
                  // 8.收集刚刚上传的图片的访问路径
                detailPicturePathList.add(picturePath);
            } else {
                 // 9.如果上传失败则返回到表单页面并显示错误消息
                modelMap.addAttribute(MyConstantUtil.ATTR_NAME_MESSAGE, MyConstantUtil.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "start-step-1";
            }
        }
        // 10.将存放了详情图片访问路径的集合存入 ProjectVO 中
        projectVO.setDetailPicturePathList(detailPicturePathList);
        // 三、后续操作
        // 1.将 ProjectVO 对象存入 Session 域
        session.setAttribute(MyConstantUtil.ATTR_NAME_TEMPLE_PROJECT, projectVO);
        // 2.以完整的访问路径前往下一个收集回报信息的页面
        return "redirect:http://localhost/project/member/to/start-step-2";

    }


}
