package com.cbd.demo.service.impl;

import com.cbd.demo.dao.IPowerDao;
import com.cbd.demo.entity.PowerEntity;
import com.cbd.demo.service.IPowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName : PowerServiceImpl
 * @Date ：2019/6/3 11:21
 * @Desc ：权限业务层实现类
 * @author：作者：吕勇
 */
@Service
public class PowerServiceImpl implements IPowerService {
     @Autowired
    private IPowerDao iPowerDao;
    @Override
    public List<PowerEntity> listPower(int reoleId) throws Exception {
        List<PowerEntity>list=iPowerDao.listAllPower(reoleId);
        return list;
    }
}
