package com.xiaoyan.crowd.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 分类 id 集合  ok
    private List<Integer> typeIdList;
    // 标签 id 集合     null
    private List<Integer> tagIdList;
    // 项目名称     ok
    private String projectName;
    // 项目描述         ok
    private String projectDescription;
    // 计划筹集的金额      ok
    private Integer money;
    // 筹集资金的天数      ok
    private Integer day;
    // 创建项目的日期
    private String createdate;
    // 头图的路径
    private String headerPicturePath;
    // 详情图片的路径
    private List<String> detailPicturePathList;
    // 发起人信息        ok
    private MemberLauchInfoVO memberLauchInfoVO;
    // 回报信息集合
    private List<ReturnVO> returnVOList;
    // 发起人确认信息
    private MemberConfirmInfoVO memberConfirmInfoVO;


}
