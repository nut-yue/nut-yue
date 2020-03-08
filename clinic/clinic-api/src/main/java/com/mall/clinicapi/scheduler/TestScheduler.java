package com.mall.clinicapi.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName : TestSchedul
 * @Date ：2020/2/19 21:04
 * @author：nut-yue
 */
@Component
public class TestScheduler {

    @Scheduled(cron = "0/10 * * * * ?")
    public void testScheduler(){
    }
}
