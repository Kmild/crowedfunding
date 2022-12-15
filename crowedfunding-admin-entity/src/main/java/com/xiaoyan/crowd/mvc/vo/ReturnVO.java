package com.xiaoyan.crowd.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 简单介绍
    private String descriptionSimple;
    // 详细介绍
    private String descriptionDetail;
    // 联系电话
    private String phoneNum;
    // 客服电话
    private String serviceNum;
    private Integer freight;
    // 是否开发票，0 - 不开发票， 1 - 开发票
    private Integer invoice;
    // 众筹结束后返还回报物品天数
    private Integer returndate;
    // 说明图片路径
    private String describPicPath;

}
