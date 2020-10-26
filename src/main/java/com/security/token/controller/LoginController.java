package com.security.token.controller;

import com.security.token.dto.UserDto;
import com.security.token.entity.UserAuthenticationRequest;
import com.security.token.service.AuthenticationService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : LoginController
 * @packageName : com.security.session.controller
 * @description : 登录控制器
 * @since : 2020/10/25
 */
@RestController
public class LoginController {

    @Resource
    AuthenticationService authenticationService;

    /**
     * 登录
     *
     * @param userAuthenticationRequest ：用户登录认证请求
     * @param httpSession               ：session对象
     * @return {String}
     */
    @RequestMapping(value = "/login", produces = {"text/plain;charset=utf-8"})
    public String login(UserAuthenticationRequest userAuthenticationRequest, HttpSession httpSession) {
        // 用户认证
        final UserDto userDto = authenticationService.userAuthentication(userAuthenticationRequest);
        // 保存session
        httpSession.setAttribute(UserDto.SESSION_USER_KEY, userDto);
        // 设置session存活时间为30秒
        httpSession.setMaxInactiveInterval(30);
        return userDto.getUserName() + "登录成功！";
    }

    /**
     * 注销
     *
     * @param session ：session对象
     * @return {String}
     */
    @GetMapping(value = "/logout", produces = {"text/plain;charset=UTF-8"})
    public String logout(HttpSession session) {
        // 退出之后清空session
        session.invalidate();
        return "退出成功";
    }

    /**
     * 资源 r1
     *
     * @param session ：session对象
     * @return {String}
     */
    @GetMapping(value = "/r/r1", produces = {"text/plain;charset=UTF-8"})
    public String r1(HttpSession session) {
        String fullName;
        Object object = session.getAttribute(UserDto.SESSION_USER_KEY);
        if (ObjectUtils.isEmpty(object)) {
            fullName = "匿名";
        } else {
            fullName = ((UserDto) object).getFullName();
        }
        return fullName + "访问资源r1";
    }

    /**
     * 资源 r2
     *
     * @param session ：session对象
     * @return {String}
     */
    @GetMapping(value = "/r/r2", produces = {"text/plain;charset=UTF-8"})
    public String r2(HttpSession session) {
        String fullName;
        Object object = session.getAttribute(UserDto.SESSION_USER_KEY);
        if (ObjectUtils.isEmpty(object)) {
            fullName = "匿名";
        } else {
            fullName = ((UserDto) object).getFullName();
        }
        return fullName + "访问资源2";
    }
}
