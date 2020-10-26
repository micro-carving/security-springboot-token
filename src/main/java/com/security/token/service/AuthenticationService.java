package com.security.token.service;


import com.security.token.dto.UserDto;
import com.security.token.entity.UserAuthenticationRequest;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : AuthenticationService
 * @packageName : com.security.session.service
 * @description : 认证服务接口
 * @since : 2020/10/25
 */
public interface AuthenticationService {

    /**
     * 用户认证，校验用户身份（账号和密码）是否合法
     *
     * @param userAuthenticationRequest ：用户认证请求
     * @return {用户数据传输实体UserDto}
     */
    UserDto userAuthentication(UserAuthenticationRequest userAuthenticationRequest);
}
