package com.xiaoyan.crowd.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoyan.crowd.exception.CrowdAddDuplicateKeyException;
import com.xiaoyan.crowd.exception.CrowdLoginFailedException;
import com.xiaoyan.crowd.mapper.AdminMapper;
import com.xiaoyan.crowd.mvc.pojo.Admin;
import com.xiaoyan.crowd.mvc.pojo.AdminExample;
import com.xiaoyan.crowd.service.AdminService;
import com.xiaoyan.crowd.util.MessageUtilForRequestType;
import com.xiaoyan.crowd.util.MyConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(null);
    }

    /**
     * 检验用户登录信息并返回admin对象
     * @param username
     * @param password
     * @return
     */
    @Override
    public Admin checkLogin(String username, String password) {

        // 0.检查用户名密码是否有效
        if(username == null || username.length() < 1){
            throw new CrowdLoginFailedException(MyConstantUtil.MESSAGE_STRING_INVALIDATE);
        }

        if(password == null || password.length() < 1){
            throw new CrowdLoginFailedException(MyConstantUtil.MESSAGE_STRING_INVALIDATE);
        }

        // 1.组装查询条件并查询
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andUserNameEqualTo(username);


        // 2.查询得到admin对象
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        // 3.判断admins对象是否为null或size!=1,是则抛出异常
        if(admins == null || admins.size() < 1){
            throw new CrowdLoginFailedException(MyConstantUtil.MESSAGE_LOGIN_FAILED);
        }
        if(admins.size()>1){
            throw new RuntimeException(MyConstantUtil.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        // 4.获取admin对象并判断是否有效
        Admin admin = admins.get(0);

        if(admin == null){
            throw new CrowdLoginFailedException(MyConstantUtil.MESSAGE_LOGIN_FAILED);
        }

        // 5.取出用户的的密码并判断 是否有效
        String userPswDB = admin.getUserPsw();
          // 如果用户在数据库中的密码为null则抛出异常
        if(userPswDB == null || userPswDB.length() < 1){
            throw new RuntimeException(MyConstantUtil.MESSAGE_SYSTEM_ERROR_LOGIN_PSW_IS_NULL);
        }

        // 6.将表单提交的密码进行md5加密
        String userPswForm = MessageUtilForRequestType.md5(password);

        // 7.进行密码的校验
        if(!Objects.equals(userPswDB,userPswForm)){
            // 密码不一致则抛出异常
            throw new CrowdLoginFailedException(MyConstantUtil.MESSAGE_LOGIN_FAILED);
        }

        // 8.一致则返回admin对象
        return admin;
    }


    /**
     * 查询用户分页信息
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        // 1.开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 2.执行查询
        AdminExample adminExample = new AdminExample();
           // 组装条件
        String value;
        if(keyword != null && keyword.length() >= 1){
            value="%"+keyword+"%";
            adminExample.createCriteria().andUserNameLike(value);
            adminExample.or().andLoginAcctLike(value);
            adminExample.or().andEmailLike(value);
        }
          // 进行查询
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        // 3.将查询结果封装到PageInfo中
        return new PageInfo<>(admins);

    }

    /**
     * 删除用户信息
     * @param adminId
     */
    @Override
    public void removeById(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    /**
     * 选择性修改admin
     * @param admin
     */
    @Override
    public void updateAdminByPrimaryKrySelective(Admin admin) {

        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof DuplicateKeyException) {
                throw new CrowdAddDuplicateKeyException(MyConstantUtil.MESSAGE_ADD_DUPLICATEKRY);
            }
        }

    }

    @Override
    public void saveAssignRoleAdmin(Integer adminId, List<Integer> assignedRoleIdList) {
        // 删除以前的数据
        adminMapper.deleteOldRelationship(adminId);

        // 保存新数据
            // 若有数据则保存
            if(assignedRoleIdList!=null &&assignedRoleIdList.size()>0) {
                adminMapper.insertNewRelationship(adminId, assignedRoleIdList);
            }
            // 否则则不插入新数据
    }

    /**
     * 根据用户名进行查询
     * @param logAcc
     * @return
     */
    @Override
    public Admin getAdminByName(String logAcc) {

        // 组装查询条件
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(logAcc);

        // 进行查询
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if(admins == null || admins.size() < 1){
            throw new RuntimeException("无此账号");
        }

        return admins.get(0);
    }


    /**
     * 添加用户信息并将密码加密
     * @param admin
     */
    @Override
    public void saveAdmin(Admin admin) {

        // 1.密码加密
        String userPsw = admin.getUserPsw();
        if(userPsw == null || userPsw.length() < 1
           || admin.getUserName() == null || admin.getUserName().length() < 1) return;
//        String pswMd5 = MessageUtilForRequestType.md5(userPsw);
        String encode = bCryptPasswordEncoder.encode(userPsw);
        admin.setUserPsw(encode);

        // 2.生成创建时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);

        // 3.执行保存并捕获名字重复的异常
        try {
            adminMapper.insert(admin);
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof DuplicateKeyException) {
                throw new CrowdAddDuplicateKeyException(MyConstantUtil.MESSAGE_ADD_DUPLICATEKRY);
            }
        }

    }



}
