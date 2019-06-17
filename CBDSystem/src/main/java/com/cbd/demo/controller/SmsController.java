package com.cbd.demo.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.cbd.demo.util.ResponseData;
import com.cbd.demo.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : SmsController
 * @Date ：2019/5/23 15:00
 * @Desc ：类的介绍： 发送短信的controller
 */
@RestController
@RequestMapping("/sms")
public class SmsController {

    /**
     * 注入短信的工具类
     * @param map
     */
    @Autowired
    private SmsUtil smsUtil;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResponseData sendSms(String mobile) {
        String sign_name = "优购测试";
        String template_code = "SMS_161595610";
        Map map = new HashMap();
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        map.put("code", code);
        try {
            SendSmsResponse sendSms = smsUtil.sendSms(mobile,
                    sign_name,
                    template_code,
                    JSON.toJSONString(map));
            System.out.println("code:"+ sendSms.getCode());
            System.out.println("message:"+ sendSms.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
        }
        ResponseData responseData = new ResponseData();
        responseData.getData().put("code", code);
        return responseData;
    }
}
