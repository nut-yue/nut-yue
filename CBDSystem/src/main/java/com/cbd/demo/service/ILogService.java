package com.cbd.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cbd.demo.bean.LogBean;
import com.cbd.demo.entity.LogEntity;

/**
 * @ClassName : ILogService
 * @Date ：2019/5/28 15:25
 * @Desc ：类的介绍：日志业务接口
 * @author：作者：胡平
 */
public interface ILogService {
    /**
     *  logBean需要转成logEntity
     *  logBean中AdminBean的adminId，需要赋值给LogEntity的adminId
     *  logDate的格式 精确到秒钟（例如 xxxx:xx:xx xx:xx:xx)
     * 添加日志
     * @param logBean 日志的实体bean
     * @return int类型、添加成功返回一个整数、添加失败返回0
     */
    int saveLog(LogBean logBean);

    /** dao层需要链表 模糊查询需要对用户名进行模糊查询
     * @param logType 日志类型：前台登录、前台操作、后台登录、后台操作 (固定的）
     * @param logOperator 操作人、判断是否为null、null不添加该查询条件、不为null的情况、进行模糊查询
     * @param logContent 操作内容 与上同理（动态条件拼接）
     * @param currentPage 当前页数
     * @param pageSize 每页条数
     * @return IPage对象  分页对象
     */
    IPage<LogEntity> listLog(String logType, String logOperator,
                             String logContent, int currentPage, int pageSize);
}
