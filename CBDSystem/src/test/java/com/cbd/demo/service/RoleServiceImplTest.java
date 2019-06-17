package com.cbd.demo.service;

import com.cbd.demo.entity.PowerEntity;
import com.cbd.demo.entity.RoleEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName : RoleServiceImplTest
 * @Date ：2019/6/3 9:03
 * @Desc ：类的介绍：角色业务实现
 * @author：作者：陈玉婷
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceImplTest {
    @Autowired
    private IRoleService roleService;


    @Test
    public void addRole() {
        RoleEntity role =new RoleEntity();
        PowerEntity power = new PowerEntity();
        power.setPowerId(11);
        PowerEntity power1 = new PowerEntity();
        power1.setPowerId(12);
        List<PowerEntity> list = new ArrayList();
        list.add(power);
        list.add(power1);

        role.setPower(list);
        role.setRoleName("个人用户====");
        int in=roleService.addRole(role);
        System.out.println(in);
//        Assert.assertEquals(in,1);
    }
    @Test
    public void showRole(){

        Assert.assertNotNull(roleService.showAllRole());
    }
    @Test
    public void getRoleCount(){
        System.out.println(roleService.getRoleCount("个人用户=============="));
    }
    @Test
    public void updatePower(){
        RoleEntity role = new RoleEntity();
        PowerEntity power = new PowerEntity();
        power.setPowerId(13);
        PowerEntity power1 = new PowerEntity();
        power1.setPowerId(15);
        List<PowerEntity> list = new ArrayList();
        list.add(power);
        list.add(power1);
        role.setRoleId(35);
        role.setPower(list);
        System.out.println(roleService.updateRole(role));
    }


}
