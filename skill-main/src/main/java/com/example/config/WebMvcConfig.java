package com.example.config;

import com.example.Interceptor.TokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 整合跨域配置 + 拦截器配置的Spring MVC核心配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // 注入你的Token拦截器（确保拦截器加了@Component注解）
    @Resource
    private TokenInterceptor tokenInterceptor;

    // ========== 跨域配置 ==========
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 对所有接口生效跨域
                .allowedOriginPatterns("*")  // 允许所有域名（生产环境建议指定具体域名）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的请求方法（必须包含OPTIONS，否则预检请求失败）
                .allowedHeaders("*")  // 允许所有请求头（包含token、content-type等）
                .allowCredentials(true)  // 允许携带Cookie/Token等凭证
                .maxAge(3600);  // 预检请求的缓存时间（秒），减少预检请求次数
    }

    // ========== 拦截器配置 ==========
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")  // 拦截所有接口
                .excludePathPatterns(  // 排除不需要拦截的接口（比如登录、授权接口）
                        "/api/common/auth/**",  // 你的授权接口
                        "/swagger-ui/**",  // swagger文档（可选）
                        "/v3/api-docs/**"   // swagger文档（可选）
                );
    }
}