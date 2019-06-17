package com.cbd.demo.util;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName : DateUtils
 * @Date ：2019/5/31 15:03
 * @Desc ：类的介绍： 格式化时间 (线程不安全）
 * @author：作者：胡平
 */
public class DateUtils {

    /**
     * 获取日期： yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String getDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("当前时间：" + sdf.format(date));
        return sdf.format(date);
    }

    /**
     * 获取时间（精确到秒钟） yyyy-MM-dd HH:mm:ss
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 获取时间：HH:mm:ss
     * @return HH:mm:ss
     */
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 计算两个时间的月份差
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static int differ(String startDate,String endDate){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime start = formatter.parseDateTime(startDate);
        DateTime end = formatter.parseDateTime(endDate);
        int months = Months.monthsBetween(start, end).getMonths();
        // 默认小于30天不计一个月,因此返回+1,合同生效1天按1月计算;
        return  months+1;
    }

}
