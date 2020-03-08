package com.mall.clinicapi.controller.goods;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.mall.clinicutil.annotation.UserAction;
import com.mall.clinicutil.data.ResponseData;
import com.mall.clinicdb.entity.GoodsEntity;
import com.mall.clinicservice.service.IGoodsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : GoodsController
 * @Date ：2019/7/22 20:38
 * @author：nut-yue
 */
@RestController
@RequestMapping("/goods")
@Api(value = "商品管理")
public class GoodsController {
    /**
     * 使用构造器注入
     */
    private final IGoodsService goodsService;

    @Autowired
    public GoodsController(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }

//    @RequiresRoles("user")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "查询商品列表", notes = "根据条件查询商品列表", response = ResponseData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商品名称"),
            @ApiImplicitParam(name = "price", value = "价格"),
            @ApiImplicitParam(name = "stock", value = "库存"),
            @ApiImplicitParam(name = "currentPage", value = "当前页数", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, defaultValue = "8")
    })
    @RequiresAuthentication
    public ResponseData listGoods(@RequestParam Map<String,Object> condition) {
        IPage<GoodsEntity> goodsEntityIPage = goodsService.listGoods(condition);
        List<GoodsEntity> list = goodsEntityIPage.getRecords();
        if (list != null) {
            ResponseData responseData = new ResponseData();
            responseData.getData().put("list", list);
            return responseData;
        }
        return ResponseData.notFound();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传", response = ResponseData.class)
    public ResponseData upLoad(@NotNull @RequestParam("fileName") MultipartFile fileName) throws IOException {
        // 保存文件
        fileName.transferTo(new File("F:/" + fileName.getOriginalFilename()));
        return ResponseData.ok();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加商品信息", notes = "手动录入商品信息", response = ResponseData.class)
    @ApiImplicitParam(name = "goodsEntity", value = "商品实体对象")
    @UserAction(action = "录入商品信息")
    public ResponseData insertGoods(@Validated  GoodsEntity goodsEntity) {
        int result = goodsService.insertGoods(goodsEntity);
        if (result != 0) {
            // 添加成功返回200
            return ResponseData.ok();
        }
        // 添加失败返回401
        return ResponseData.unauthorized();
    }

    @RequestMapping(value = "/getGoods", method = RequestMethod.GET)
    @ApiOperation(value = "查询商品信息", notes = "根据商品的id查询单个商品的具体信息", response = ResponseData.class)
    @ApiImplicitParam(name = "goodsId", value = "商品id", defaultValue = "1")
    public ResponseData getGoods(@RequestParam String goodsId) {
        GoodsEntity goods = goodsService.getGoods(goodsId);
        if (goods != null) {
            ResponseData responseData = new ResponseData();
            responseData.getData().put("goods", goods);
            // 查询到结果则返回查询结果
            return responseData;
        }
        // 未查询到返回404
        return ResponseData.notFound();
    }

    @RequestMapping(value = "/removeBatch", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除商品", notes = "根据id批量删除商品信息", response = ResponseData.class)
    @ApiImplicitParam(name = "ids", value = "商品id")
    public ResponseData removeBatch(String ids) {
        if (StringUtils.isNotEmpty(ids)) {
            String[] goodsAry = ids.split(",");
            List<String> goodsIds = Arrays.asList(goodsAry);
            int result = goodsService.removeBatch(goodsIds);
            if (result != 0) {
                return ResponseData.ok();
            }
        }
        return ResponseData.unauthorized();
    }

    @RequestMapping(value = "/updateGoods", method = RequestMethod.PUT)
    @ApiOperation(value = "修改商品信息", notes = "根据商品的id修改商品信息", response = ResponseData.class)
    @ApiImplicitParam(name = "goodsEntity", value = "商品实体对象")
    public ResponseData updateGoods(GoodsEntity goodsEntity) {
        int result = goodsService.updateGoods(goodsEntity);
        if (result != 0) {
            return ResponseData.ok();
        }
        return ResponseData.unauthorized();
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ApiOperation(value = "导出", notes = "导出商品列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "condition", value = "查询条件"),
            @ApiImplicitParam(name = "response", value = "响应对象")
    })
    public void export( @RequestParam Map<String,Object> condition, HttpServletResponse response) throws IOException {
        String fileName = "商品信息.xls";
        response.setContentType("application/excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8"));
        // 创建表格
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet1");
        int rowNum = 0;
        // 创建第一行（表头）
        HSSFRow row = sheet.createRow(rowNum);
        String[] tableHeaders = {"商品名", "价格", "库存", "商品简介",  "商品类型"};
        for (int i = 0; i < tableHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(tableHeaders[i]);
            cell.setCellValue(text);
        }
        // 设置行高
        row.setHeightInPoints(20);
        // 查询数据库得到商品集合，遍历集合写入excel
        IPage<GoodsEntity> goodsEntityIPage = goodsService.listGoods(condition);
        List<GoodsEntity> list = goodsEntityIPage.getRecords();
        for (GoodsEntity goodsEntity : list) {
            rowNum++;
            HSSFRow rows = sheet.createRow(rowNum);
            rows.createCell(0).setCellValue(goodsEntity.getGoodsName());
            rows.createCell(1).setCellValue(String.valueOf(goodsEntity.getPrice()));
            rows.createCell(2).setCellValue(goodsEntity.getStock());
            rows.createCell(3).setCellValue(goodsEntity.getInfo());
            rows.createCell(4).setCellValue(goodsEntity.getType());
        }
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
    }

}
