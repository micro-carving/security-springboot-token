package com.security.token.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 认证请求实体
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserAuthenticationRequest extends BaseEntity{

    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
}
