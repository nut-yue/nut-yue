package com.cbd.demo.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName : AdministratorBean
 * @Date ：2019/5/28 14:25
 * @Desc ：类的介绍： 管理员Bean
 * @author：作者：王佳伟
 */
@Data
public class AdministratorBean {
    /**管理员编号*/
    private  Integer administratorId;
    /**管理员姓名*/
    private String administratorName;
    /**管理员电话*/
    private String administratorPhone;
    //用户外键
    private AdminBean adminBean;
    private String[] type;
}
