package com.example.service.impl;

import com.example.entry.Result;
import com.example.entry.enums.ResultCode;
import com.example.service.CommonSkill;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CommonSkillImpl implements CommonSkill {

    /**
     * 权限校验（简单版：token不为空且长度>0）
     */
    @Override
    public boolean checkAuth(String token) {
        return token != null && !token.trim().isEmpty() && token.length() > 10;
    }

    /**
     * 获取真实客户端IP
     */
    @Override
    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取当前时间戳（毫秒）
     */
    @Override
    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 统一成功响应
     */
    @Override
    public Result<Object> returnSuccess(ResultCode data, String token) {
        // 直接调用Result的静态success方法，自动封装code=200、msg=success、data=data
        return Result.success(data,token);

    }

    @Override
    public Result<Object> returnSuccess(ResultCode data) {
        return Result.success(data);
    }

    /**
     * 统一失败响应
     */
    @Override
    public Result<Object>  returnError(int code, String msg) {
        return Result.fail(code, msg);
    }
}