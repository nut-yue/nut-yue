package com.mall.clinicapi.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.clinicdb.entity.CustomEntity;
import com.mall.clinicservice.user.ICustomService;
import com.mall.clinicutil.common.ParamUtil;
import com.mall.clinicutil.data.ResponseData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 客户管理
 * @ClassName : CustomController
 * @Date ：2020/2/23 20:54
 * @author：nut-yue
 */
@RestController
@RequestMapping("/custom")
public class CustomController {

    @Autowired
    private ICustomService customService;
    @Autowired
    private ParamUtil paramUtil;

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @ApiOperation(value = "新增客户信息",notes = "手动维护客户信息",response = ResponseData.class)
    @ApiImplicitParam(name = "customEntity", value = "客户对象")
    public ResponseData insertCustom (CustomEntity customEntity){
        int result = customService.insertCustom(customEntity);
        if(result!=0){
            return ResponseData.ok();
        }
        return ResponseData.unauthorized();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除客户信息",notes = "手动维护客户信息",response = ResponseData.class)
    @ApiImplicitParam(name = "customIds", value = "客户id",defaultValue = "1")
    public ResponseData deleteBatchCustom(String customIds){
        if (StringUtils.isNotBlank(customIds)) {
            String [] ids = customIds.split(",");
            List <String> customs = CollectionUtils.arrayToList(ids);
            int result = customService.deleteBatchCustom(customs);
            if (result!=0) {
                return ResponseData.ok();
            }
        }
        return ResponseData.notFound();

    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ApiOperation(value = "修改客户信息",notes = "手动维护客户信息",response = ResponseData.class)
    @ApiImplicitParam(name = "customEntity", value = "客户对象")
    public ResponseData updateCustom(CustomEntity customEntity){
        int result = customService.updateCustom(customEntity);
        if(result!=0){
            return ResponseData.ok();
        }
        return ResponseData.unauthorized();
    }

    @GetMapping("/listCustoms")
    @ApiOperation(value = "查询客户列表",notes = "手动维护客户信息",response = ResponseData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页数", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, defaultValue = "8")
    })
    public ResponseData listCustoms(@RequestParam Map<String,Object> params){
        // 取出分页条件，封装分页对象
        int currentPage = MapUtils.getIntValue(params,"currentPage");
        int pageSize = MapUtils.getIntValue(params,"pageSize");
        Page<CustomEntity> page = new Page<>(currentPage,pageSize);
        params = paramUtil.getParam(params);
        IPage<CustomEntity> pages = customService.listCustoms(page, params);
        // 查询结果不为空，则封装结果并返回
        if (ObjectUtils.isNotEmpty(pages.getRecords())){
            ResponseData responseData = new ResponseData();
            responseData.getData().put("customs",pages.getRecords());
            return responseData;
        }
        return ResponseData.notFound();
    }

    @GetMapping("/getCustom")
    @ApiOperation(value = "查询客户信息",notes = "手动维护客户信息",response = ResponseData.class)
    @ApiImplicitParam(name = "customId", value = "客户id",defaultValue = "1")
    public ResponseData getCustom(String customId){
        CustomEntity custom = customService.getCustom(customId);
        if (ObjectUtils.isNotEmpty(custom)) {
            ResponseData responseData = new ResponseData();
            responseData.getData().put("custom",custom);
            return responseData;
        }
        return ResponseData.notFound();
    }

}
