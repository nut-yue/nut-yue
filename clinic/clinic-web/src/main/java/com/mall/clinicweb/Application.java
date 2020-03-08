package com.mall.clinicweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName : Application
 * @Date ：2019/12/23 15:42
 * @author：nut-yue
 */
@MapperScan("com.mall.clinicdb.mapper")
@EnableScheduling
@EnableTransactionManagement
@EnableJms // 运行消息队列
@SpringBootApplication(scanBasePackages = {"com.mall.clinicapi","com.mall.clinicdb",
        "com.mall.clinicservice","com.mall.clinicutil","com.mall.clinicweb"})
@EnableCaching
public class Application  {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
