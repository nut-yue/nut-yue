package com.cbd.demo.service;/**
 * Created by 123 on 2019/5/28.
 */

import com.cbd.demo.bean.PeopleNumBean;

import java.util.List;
import java.util.Map;

/**
 * @ClassName : IPeopleNumService
 * @Date ：2019/5/28 15:31
 * @Desc ：接口的介绍：在线人数统计业务逻辑接口
 * @author：作者： 胡平
 * 注意：
 *      peopleNumDate的格式 2018-05-21
 *      peopleNumTime的格式 9:00:00
 */
public interface IPeopleNumService {

    /**
     * 添加在线人数
     * peopleNumDate 和 peopleNumTime两个字段是在service进行封装
     * @param peopleNumBean 在线人数统计的实体bean
     * @return int 类型 成功返回影响函数、失败返回0
     */
    int savePeopleNum(PeopleNumBean peopleNumBean);


    /**
     * 通过日期来查询记录
     *  该参数的格式必须是：2018-05-21，
     *                      而且只能查询以前的在线人数，不能查询本天，因为查询本天的在线人数，存在数据的缺失
     * @return List集合
     */
    Map<String, List<Integer>> listPeopleNum();

}
