package com.security.token.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : User
 * @packageName : com.security.session.entity
 * @description : 用户实体
 * @since : 2020/10/25
 */
@Data
@AllArgsConstructor
public class User implements Serializable {

    /**
     * 编号
     */
    private String id;
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
