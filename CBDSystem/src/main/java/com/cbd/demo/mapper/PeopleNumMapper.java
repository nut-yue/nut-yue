package com.cbd.demo.mapper;/**
 * Created by 123 on 2019/5/28.
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cbd.demo.entity.PeopleNumEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName : IPeopleNumService
 * @Date ：2019/5/28 15:31
 * @Desc ：接口的介绍：在线人数统计的mapper
 * @author：作者： 胡平
 */
public interface PeopleNumMapper extends SqlMapper,BaseMapper<PeopleNumEntity> {

    @Select("SELECT sum(peopleNums) as peopleNums, peopleNumTime FROM  " +
            "t_peoplenum WHERE peopleNumDate >= #{0} GROUP  BY peopleNumTime ")
    public List<PeopleNumEntity> sumPeopleNum(String peopleNumDate);
}
