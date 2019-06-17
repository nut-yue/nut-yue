package com.cbd.demo.util;

/**
 * @ClassName : MoneyUtil
 * @Date ：2019/6/1 22:46
 * @Desc ：类的介绍：用于计算提前节约时的实际账单金额
 * @author：岳超
 */
public class MoneyUtil {

    public static int moneyCount(String startDate,String endDate,int DealMoney){
        int usedMonth = DateUtils.differ(startDate,DateUtils.getDate());
        int totalMonth = DateUtils.differ(startDate,endDate);
        // 合约总金额=合约总金额-（总金额/合约使用总月数）*（总月份-已使用月份）
        return DealMoney-(DealMoney/totalMonth)*(totalMonth-usedMonth);
    }
}
