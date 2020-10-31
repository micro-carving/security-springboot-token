package com.security.token.service.impl;

import cn.hutool.core.map.MapUtil;
import com.security.token.entity.User;
import com.security.token.entity.UserAuthenticationRequest;
import com.security.token.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * 用户信息
     */
    private Map<String, User> userMap = new HashMap<>();

    // 使用构造块来模拟用户信息
    {
        Set<String> authorities1 = new HashSet<>();
        // 为John添加p1的权限
        authorities1.add("p1");
        Set<String> authorities2 = new HashSet<>();
        // 为Lucy添加p2的权限
        authorities2.add("p2");
        userMap.put("John", new User("John", "John", "约翰", "110", authorities1));
        userMap.put("Lucy", new User("Lucy", "Lucy", "露西", "120", authorities2));
    }

    /**
     * 根据账号查询用户信息
     *
     * @param userName ：账号
     * @return {用户数据传输实体}
     */
    public User getUser(String userName) {
        return userMap.get(userName);
    }

    /**
     * 用户认证，校验用户身份（账号和密码）是否合法
     *
     * @param userAuthenticationRequest ：用户认证请求
     * @return {用户数据传输实体User}
     */
    @Override
    public Map<String, Object> userAuthentication(UserAuthenticationRequest userAuthenticationRequest) {
        String message = "";
        String userData = "";
        Map<String, Object> resultMap = MapUtil.newHashMap();

        // 校验参数是否为空
        if (ObjectUtils.isEmpty(userAuthenticationRequest)
                || StringUtils.isEmpty(userAuthenticationRequest.getUserName())
                || StringUtils.isEmpty(userAuthenticationRequest.getPassword())) {
            resultMap.put(message, "账号或者密码为空！");
            log.info("--- 账号或者密码为空！ ---");
        }
        // 根据账号去查询数据库,这里测试程序采用模拟方法
        final User user = getUser(userAuthenticationRequest.getUserName());
        resultMap.put(userData, user);
        // 校验用户是否为空
        if (ObjectUtils.isEmpty(user)) {
            resultMap.put(message, "查询的用户不存在！");
            log.info("--- 查询的用户不存在！ ---");
        }
        // 校验账号与密码是否错误
        if (!userAuthenticationRequest.getPassword().equals(user.getPassword())) {
            resultMap.put(message, "账号或者密码错误！");
            log.info("--- 账号或者密码错误！ ---");
        }
        // 认证通过，返回用户信息
        return resultMap;
    }
}
