package com.xiaoyan.crowd.api.impl;

import com.xiaoyan.crowd.api.MemberService;
import com.xiaoyan.crowd.mapper.MemberPOMapper;
import com.xiaoyan.crowd.mvc.pojo.MemberPO;
import com.xiaoyan.crowd.mvc.pojo.MemberPOExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcc(String loginAcc) {

        MemberPOExample memberPOExample = new MemberPOExample();
        memberPOExample.createCriteria().andLoginAcctEqualTo(loginAcc);

        List<MemberPO> memberPOS = memberPOMapper.selectByExample(memberPOExample);

        return (memberPOS!=null && memberPOS.size()>0) ? memberPOS.get(0) : null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}
