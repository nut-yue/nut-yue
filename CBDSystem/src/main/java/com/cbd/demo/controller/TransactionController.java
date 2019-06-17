package com.cbd.demo.controller;

import com.cbd.demo.annotation.Introduce;
import com.cbd.demo.bean.TransactionBean;
import com.cbd.demo.service.ITransactionService;
import com.cbd.demo.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：杨强
 * @date ：Created in 2019/6/3 0003 9:27
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private ITransactionService transactionService;

    @Introduce(desc="添加交易记录")
    @RequestMapping(value = "addTransaction",method = RequestMethod.GET )
    public ResponseData addTransaction(TransactionBean transactionBean){
        ResponseData responseData=new ResponseData();
        //调用添加业务
        int result=transactionService.saveTransaction(transactionBean);
        //创建map集合保存结果
        Map<String,Object> map=new HashMap<>();
        //根据结果判断添加是否成功
        if(result==1){
            map.put("result","true");
            responseData.setData(map);
        }else {
            map.put("result","false");
            responseData.setData(map);
        }
        return responseData;
    }
    @Introduce(desc="根据id查询交易记录")
    @RequestMapping(value = "findTransactionById",method = RequestMethod.GET)
    public ResponseData getTransactionById(Integer id){
        ResponseData responseData=new ResponseData();
        //创建map集合保存结果
        Map<String,Object> map=new HashMap<>();
        TransactionBean transactionBean=transactionService.getTransaction(id);
        map.put("result",transactionBean);
        responseData.setData(map);
        return responseData;
    }

    @Introduce(desc="按车位id查询交易记录")
    @RequestMapping(value = "findByCar",method = RequestMethod.GET)
    public ResponseData getByCarportId(int carportId){
        TransactionBean trans = transactionService.getByCarport(carportId);
        if(trans!=null){
            ResponseData responseData = new ResponseData();
            responseData.getData().put("trans",trans);
            return responseData;
        }
        return ResponseData.notFound();
    }
}
