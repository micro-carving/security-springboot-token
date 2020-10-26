package com.security.token.config;

import com.security.token.interceptor.AuthenticationInterceptor;
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
 * @className : TODO
 * @packageName : com.security.token.config
 * @description : TODO
 * @since : 2020/10/26
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.security.token"
        , includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class InterceptorConfig implements WebMvcConfigurer {

    @Resource
    AuthenticationInterceptor authenticationInterceptor;

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
     * 为视图添加拦截器
     *
     * @param registry 视图拦截器注册中心
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor).addPathPatterns("/r/**");
    }
}
