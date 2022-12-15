package com.xiaoyan.crowd.api.impl;

import com.xiaoyan.crowd.api.ProjectService;
import com.xiaoyan.crowd.mapper.*;
import com.xiaoyan.crowd.mvc.pojo.MemberLaunchInfoPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private ReturnPOMapper returnPOMapper;

    @Autowired
    private TagPOMapper tagPOMapper;

    @Autowired
    private TypePOMapper typePOMapper;





}
