package com.security.token.service;


import com.security.token.entity.User;
import com.security.token.entity.UserAuthenticationRequest;

import java.util.Map;

/**
 * 认证服务接口
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/25
 */
public interface AuthenticationService {

    /**
     * 用户认证，校验用户身份（账号和密码）是否合法
     *
     * @param userAuthenticationRequest ：用户认证请求
     * @return {String-Object键值对}
     */
    Map<String, Object> userAuthentication(UserAuthenticationRequest userAuthenticationRequest);
}
