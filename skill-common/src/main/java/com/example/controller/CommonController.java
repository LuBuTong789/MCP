package com.example.controller;

import com.example.entry.Result;
import com.example.entry.enums.ResultCode;
import com.example.service.CommonSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    private CommonSkill commonSkill;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    //登录校验接口主要是为了校验redis内是否有对应的密码,发放token。
    @GetMapping("/auth/check")
    public Result<Object> checkAuth(@RequestParam String code) {
        boolean pass = true;
        if (pass) {
            // 生成一个随机 Token
            String token = UUID.randomUUID().toString();
            String key = "login:token:" + token;
            // 存入 Redis，并设置 12 小时过期
            redisTemplate.opsForValue().set(key, token, Duration.ofHours(12));
            // 返回给前端的 Token
            return commonSkill.returnSuccess(ResultCode.SUCCESS,token);
        } else {
            return commonSkill.returnError(401, "token无效");
        }
    }















    /**
     * 单独的 Token 校验接口
     */
    @GetMapping("/auth/verify")
    public Result<Object> verifyToken(@RequestParam String token) {
        String key = "login:token:" + token;
        // 检查 Redis 中是否存在该 Token（且未过期）
        Boolean exists = redisTemplate.hasKey(key);

        if (Boolean.TRUE.equals(exists)) {
            // Token 有效
            return commonSkill.returnSuccess(ResultCode.SUCCESS);
        } else {
            // Token 无效/过期
            return commonSkill.returnError(HttpStatus.UNAUTHORIZED.value(), "Token无效或已过期");
        }
    }

}