package com.security.token.entity;

import lombok.Data;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : AuthenticationRequest
 * @packageName : com.security.session.entity
 * @description : 认证请求实体
 * @since : 2020/10/25
 */
@Data
public class UserAuthenticationRequest {

    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
}
