package com.security.token.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户实体
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class User extends BaseEntity {

    /**
     * 账号
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户全称
     */
    private String fullName;
    /**
     * 用户手机
     */
    private String mobile;
    /**
     * 用户权限，p1和p2权限
     */
    private Set<String> authorities;
}
