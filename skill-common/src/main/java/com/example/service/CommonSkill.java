package com.example.service;

import com.example.entry.Result;
import com.example.entry.enums.ResultCode;
import jakarta.servlet.http.HttpServletRequest;

public interface CommonSkill {

    // 权限校验
    boolean checkAuth(String token);

    // 获取客户端IP
    String getClientIp(HttpServletRequest request);

    // 获取当前时间戳
    long getTimestamp();

    // 统一成功返回

    Result<Object> returnSuccess(ResultCode data, String token);

    Result<Object> returnSuccess(ResultCode data);

    // 统一失败返回
    Result<Object>  returnError(ResultCode data);
}