package com.example.entry;

import com.example.entry.enums.ResultCode;
import lombok.Data;

/**
 * 统一接口响应格式
 */
@Data
public class Result<T> {
    // 响应码：200成功，500失败，401未授权/密码不存在

    private Integer code;   // 响应码（对应ResultCode的code）
    private String msg;
    private String token;


    public Result(ResultCode data) {
        this.code = data.getCode();
        this.msg = data.getMsg();
    }
    // 1. 必须显式添加【无参构造器】，解决"应为无实参"编译错误
    public  Result(ResultCode data, String token) {
        this.code = data.getCode();
        this.msg = data.getMsg();
        this.token=token;
    }




    public Result(Object code, String msg) {
    }
    // 成功响应（带数据）
    public static <T> Result<T> success(ResultCode data, String token) {
        return new Result<>(data,token);
    }
    public static <T> Result<T> success(ResultCode data) {
        return new Result<>(data);
    }
    // 失败响应（自定义状态码+消息）
    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg);
    }

}