package com.mall.clinicutil.data;

import java.util.HashMap;
import java.util.Map;

/**
 * restful api接口响应数据工具类
 */
public class ResponseData {
    private int code = 200;        //响应状态码
    private String message = "ok"; //响应状态信息
    private Map<String, Object> data = new HashMap<String, Object>(); //响应数据

    public ResponseData() {

    }

    public ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseData ok() {
        return new ResponseData(200, "ok");
    }

    public static ResponseData notFound() {
        return new ResponseData(404, "not found");
    }

    public static ResponseData unauthorized() {
        return new ResponseData(401, "unauthorized");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
