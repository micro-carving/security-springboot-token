package com.security.token.service.impl;

import com.security.token.dto.UserDto;
import com.security.token.entity.UserAuthenticationRequest;
import com.security.token.service.AuthenticationService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : AuthenticationServiceImpl
 * @packageName : com.security.session.service.impl
 * @description : 认证业务实现类
 * @since : 2020/10/25
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * 用户信息
     */
    private Map<String, UserDto> userDtoMap = new HashMap<>();

    // 使用构造块来模拟用户信息
    {
        Set<String> authorities1 = new HashSet<>();
        // 为luna添加p1的权限
        authorities1.add("p1");
        Set<String> authorities2 = new HashSet<>();
        // 为monkey-king添加p2的权限
        authorities2.add("p2");
        userDtoMap.put("luna", new UserDto("s1", "luna", "520", "紫霞仙子——露娜", "520", authorities1));
        userDtoMap.put("monkey-king", new UserDto("s2", "monkey-king", "1314", "至尊宝——猴王", "1314", authorities2));
    }

    /**
     * 根据账号查询用户信息
     *
     * @param userName ：账号
     * @return {用户数据传输实体}
     */
    public UserDto getUserDto(String userName) {
        return userDtoMap.get(userName);
    }

    /**
     * 用户认证，校验用户身份（账号和密码）是否合法
     *
     * @param userAuthenticationRequest ：用户认证请求
     * @return {用户数据传输实体UserDto}
     */
    @Override
    public UserDto userAuthentication(UserAuthenticationRequest userAuthenticationRequest) {
        // 校验参数是否为空
        if (ObjectUtils.isEmpty(userAuthenticationRequest)
                || StringUtils.isEmpty(userAuthenticationRequest.getUserName())
                || StringUtils.isEmpty(userAuthenticationRequest.getPassword())) {
            throw new RuntimeException("账号或者密码为空！");
        }
        // 根据账号去查询数据库,这里测试程序采用模拟方法
        final UserDto userDto = getUserDto(userAuthenticationRequest.getUserName());
        // 校验用户是否为空
        if (ObjectUtils.isEmpty(userDto)) {
            throw new RuntimeException("查询的用户不存在！");
        }
        // 校验账号与密码是否错误
        if (!userAuthenticationRequest.getPassword().equals(userDto.getPassword())) {
            throw new RuntimeException("账号或者密码错误");
        }
        // 认证通过，返回用户信息
        return userDto;
    }
}
