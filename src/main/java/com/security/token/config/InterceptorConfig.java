package com.security.token.config;

import com.security.token.interceptor.AuthenticationInterceptor;
import com.security.token.interceptor.JwtAuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : InterceptorConfig
 * @packageName : com.security.token.config
 * @description : 拦截器配置
 * @since : 2020/10/26
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    JwtAuthorizationInterceptor jwtAuthorizationInterceptor;

    /**
     * 为视图添加对应的控制器
     *
     * @param registry 视图控制器注册中心
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

    /**
     * 为视图添加拦截器，拦截所有请求
     *
     * @param registry 视图拦截器注册中心
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthorizationInterceptor).addPathPatterns("/r/**");
    }

    /**
     * 此方式是将拦截器对象注入进来，不需要在来拦截器上配置@Component注解
     *
     * @return JwtAuthorizationInterceptor
     */
    @Bean
    public JwtAuthorizationInterceptor jwtAuthorizationInterceptor() {
        return new JwtAuthorizationInterceptor();
    }
}
