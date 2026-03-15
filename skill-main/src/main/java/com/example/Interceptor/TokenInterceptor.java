package com.example.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, Object> redisTemplate;

    public TokenInterceptor(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 从请求头获取 Token（推荐方式，比请求参数更安全）
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            // 无 Token，直接返回 401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("请先登录");
            return false;
        }

        // 2. 校验 Token 是否有效
        String key = "login:token:" + token;
        Boolean exists = redisTemplate.hasKey(key);
        if (Boolean.FALSE.equals(exists)) {
            // Token 无效/过期
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token无效或已过期");
            return false;
        }

        // 3. Token 有效，放行请求
        return true;
    }
}