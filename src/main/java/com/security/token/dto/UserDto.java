package com.security.token.dto;


import com.security.token.entity.User;

import java.util.Set;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : UserDto
 * @packageName : com.security.session.dto
 * @description : 用户信息数据传输实体
 * @since : 2020/10/25
 */
public class UserDto extends User {
    /**
     * 用户对应的session键
     */
    public static final String SESSION_USER_KEY = "_user";

    public UserDto(String id, String userName, String password, String fullName, String mobile, Set<String> authorities) {
        super(id, userName, password, fullName, mobile, authorities);
    }
}
