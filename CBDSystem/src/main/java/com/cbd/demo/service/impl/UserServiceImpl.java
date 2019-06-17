package com.cbd.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cbd.demo.bean.AdminBean;
import com.cbd.demo.bean.RoleBean;
import com.cbd.demo.bean.UserBean;
import com.cbd.demo.dao.IAdminDao;
import com.cbd.demo.dao.IRoleDao;
import com.cbd.demo.dao.IUserDao;
import com.cbd.demo.entity.AdminEntity;
import com.cbd.demo.entity.RoleEntity;
import com.cbd.demo.entity.UserEntity;
import com.cbd.demo.service.IUserService;
import com.cbd.demo.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : UserServiceImpl
 * @Date ：2019/5/31 18:47
 * @Desc ：类的介绍：用户操作业务实现类
 * @author：作者：周陆成
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IAdminDao adminDao;
    @Autowired
    private IRoleDao roleDao;
    /**
     * 根据用户编号查询用户详细信息,根据交易次数和投诉次数计算出信誉值。
     * 信誉度=（1-被投诉次数/总交易次数）*5
     * @param id 用户id
     * @return 个人用户对象
     */
    @Override
    public UserBean getUser(int id) {
        //创建userbean
        UserBean userBean=null;
        //创建adminEntity
        AdminEntity adminEntity=null;
        //创建adminBean
        AdminBean adminBean=null;
        try {
            userBean=new UserBean();
            adminEntity=new AdminEntity();
            adminBean=new AdminBean();
            //条件构造器
            QueryWrapper<UserEntity>wrapper=new QueryWrapper<>();
            //根据user查找admin
            wrapper.eq("adminId",id);
                //按id获取用户信息
                UserEntity userEntity =userDao.getByUserId(wrapper);
                //将userEntity转化为bean
                BeanUtils.copyProperties(userEntity,userBean);

                //条件构造器
                QueryWrapper<AdminEntity>queryWrapper=new QueryWrapper<>();
                //根据user查找admin
                queryWrapper.eq("adminId",id);
                //获取admin对象
                adminEntity=adminDao.selectOne(queryWrapper);
                //强转adminBean
                BeanUtils.copyProperties(adminEntity,adminBean);
                //将adminbean访入user中
               userBean.setAdminBean(adminBean);

                //信誉度计算公式 判断是否交易笔数是否为有效 例如返回为负数
                if(userEntity.getUserDeal()!=0) {
                    //信誉度=（1-被投诉次数/总交易次数）*5
                    userBean.setReputation((1 - (userEntity.getUserComplaint()/userEntity.getUserDeal()))*5);
                }else {
                    //如果交易次数为0 那么信誉度也为0
                    userBean.setReputation(0);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userBean;
    }

    /**
     * 根据登录用户id修改用户信息
     * @param user 个人用户对象
     * @return 影响行数
     */
    @Override
    public int updateUser(UserBean user) {
        UserEntity userEntity=new UserEntity();
        int sel=0;
        try {
            //将entity转化为bean
            BeanUtils.copyProperties(user,userEntity);
            //做修改操作
             sel= userDao.updateById(userEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sel;
    }

    /**
     * 添加用户
     * @param user 个人用户对象
     * @return 是否添加完成
     */
    @Override
    public int saveUsers(UserBean user) {
        System.out.println(user);
        //user添加
        int add=0;
        //admin添加
        int addAdmin=0;
        try {
            //创建一个userEntity
            UserEntity userEntity=new UserEntity();
            //将userbean 转化为 userentity
            BeanUtils.copyProperties(user,userEntity);
            //判断adminbean是否为空 如果为空则不添加
            int adminId=1;
            if (user.getAdminBean()!=null){
                adminId = user.getAdminBean().getAdminId();
            }
            userEntity.setAdminId(adminId);
            //调用userdao中添加user方法
            add=userDao.addUser(userEntity);
            if(user.getAdminBean()!=null){

                //创建一个adminentity
                AdminEntity adminEntity = new AdminEntity();
                //转化
                BeanUtils.copyProperties(user.getAdminBean(),adminEntity);
                //md5加密
                adminEntity.setAdminPassword(MD5Utils.md5(adminEntity.getAdminPassword()));
                //设置用户类型为 个人用户
                adminEntity.setAdminRoleType(1);
                adminEntity.setRoleId(7);
                //调用admindao的添加方法
                addAdmin=adminDao.insert(adminEntity);
                //进行条件判断
                if(addAdmin!=0){
                    return add;
                }else {
                    return 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return add;
    }
    /**
     * 添加用户
     * @param user 个人用户对象
     * @return 是否添加完成
     */
    @Override
    public int saveUser(UserBean user) {
//        System.out.println(user);
        //user添加
        int add=0;
        //admin添加
        int addAdmin=0;
        try {
            //条件构造器
            QueryWrapper<AdminEntity>queryWrapper=new QueryWrapper<>();
            //根据user查找admin
            queryWrapper.eq("adminName",user.getAdminBean().getAdminName());
            //判断用户名是否重复
            AdminEntity admins=adminDao.selectOne(queryWrapper);
            if(admins!=null){
                return 0;
            }
            //创建一个AdminEntity
            AdminEntity admin = new AdminEntity();
            BeanUtils.copyProperties(user.getAdminBean(),admin);
            //给角色分配权限
            admin.setAdminRoleType(1);
            admin.setRoleId(7);
            //md5加密
            admin.setAdminPassword(MD5Utils.md5(admin.getAdminPassword()));
            adminDao.insert(admin);
            int id =admin.getAdminId();

            //创建一个userEntity
            UserEntity userEntity=new UserEntity();
            //将userbean 转化为 userentity
            BeanUtils.copyProperties(user,userEntity);
            userEntity.setAdminId(id);

            add= userDao.addUser(userEntity);


        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return add;
    }
    @Override
    public UserBean getUserId(int userId) {
        //创建userbean
        UserBean userBean=null;
        //创建adminEntity
        AdminEntity adminEntity=null;
        //创建adminBean
        AdminBean adminBean=null;
        try {
            userBean=new UserBean();
            adminEntity=new AdminEntity();
            adminBean=new AdminBean();
            //按id获取用户信息
            UserEntity userEntity =userDao.getById(userId);
            //将userEntity转化为bean
            BeanUtils.copyProperties(userEntity,userBean);

            //条件构造器
            QueryWrapper<AdminEntity>queryWrapper=new QueryWrapper<>();
            //根据user查找admin
            queryWrapper.eq("adminId",userEntity.getAdminId());
            //获取admin对象
            adminEntity=adminDao.selectOne(queryWrapper);
            //强转adminBean
            BeanUtils.copyProperties(adminEntity,adminBean);
            //将adminbean访入user中
            userBean.setAdminBean(adminBean);

            //信誉度计算公式 判断是否交易笔数是否为有效 例如返回为负数
            if(userEntity.getUserDeal()!=0) {
                //信誉度=（1-被投诉次数/总交易次数）*5
                userBean.setReputation((1 - (userEntity.getUserComplaint()/userEntity.getUserDeal()))*5);
            }else {
                //如果交易次数为0 那么信誉度也为0
                userBean.setReputation(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userBean;
    }
}
