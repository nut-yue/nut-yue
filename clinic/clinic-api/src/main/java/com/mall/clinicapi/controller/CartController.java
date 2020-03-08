package com.mall.clinicapi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mall.clinicdb.entity.CartEntity;
import com.mall.clinicservice.service.ICartService;
import com.mall.clinicutil.data.ResponseData;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : CartController
 * @Date ：2020/1/21 14:11
 * @author：nut-yue
 */
@RestController
@RequestMapping("/cart")
@Api("购物车管理")
public class CartController {

    @Autowired
    private ICartService cartService;

    @RequestMapping("/list")
    public ResponseData listCart(Map <String,Object> condition){
        IPage<CartEntity> page = cartService.listCart(condition);
        List <CartEntity> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)){
            ResponseData responseData = new ResponseData();
            responseData.getData().put("list",records);
            return responseData;
        }
        return ResponseData.notFound();
    }

    @RequestMapping("/delete")
    public ResponseData deleteCart(String ids){
        if(StringUtils.isNotEmpty(ids)){
            String[] cartIds = ids.split(",");
            List carts = Arrays.asList(cartIds);
            cartService.deleteCart(carts);
            return ResponseData.ok();
        }
        return ResponseData.unauthorized();
    }

    @RequestMapping("/add")
    public ResponseData insertCart(CartEntity cartEntity){
        cartService.insertGoods(cartEntity);
        return ResponseData.ok();
    }

    @RequestMapping("/update")
    public ResponseData updateCart(CartEntity cartEntity){
        cartService.updateCart(cartEntity);
        return ResponseData.ok();
    }
}
