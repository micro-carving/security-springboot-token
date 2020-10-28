package com.security.token.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.security.token.annotation.AuthToken;
import com.security.token.annotation.PassToken;
import com.security.token.dto.UserDto;
import com.security.token.entity.UserAuthenticationRequest;
import com.security.token.service.AuthenticationService;
import com.security.token.utils.TokenGenerator;
import com.security.token.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

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
    @Resource
    TokenGenerator<UserDto> tokenGenerator;

    /**
     * 登录
     *
     * @param userAuthenticationRequest : 认证请求实体
     * @return : {String}
     */
    @PostMapping(value = "/login")
    public String login(@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        Map<String, Object> map = MapUtil.newHashMap();
        // 用户认证
        final UserDto userDto = authenticationService.userAuthentication(userAuthenticationRequest);
        // 生成token
        String token = tokenGenerator.getTokenByJwt(userDto);
        map.put("token", token);
        map.put("message", userDto.getUserName() + "登录成功！");
        return JSONUtil.toJsonStr(map);
    }

    /**
     * 资源 r1
     *
     * @param request ：request对象
     * @return {String}
     */
    @AuthToken
    @GetMapping(value = "/r/r1")
    public String r1(HttpServletRequest request) {
        String fullName;
        Object object = request.getAttribute(UserDto.REQUEST_USER_KEY);
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
     * @param request ：request对象
     * @return {String}
     */
    @GetMapping(value = "/r/r2")
    public String r2(HttpServletRequest request) {
        String fullName;
        Object object = request.getAttribute(UserDto.REQUEST_USER_KEY);
        if (ObjectUtils.isEmpty(object)) {
            fullName = "匿名";
        } else {
            fullName = ((UserDto) object).getFullName();
        }
        return fullName + "访问资源2";
    }

    /**
     * 显示通过验证的信息
     *
     * @return {String}
     */
    @PassToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }
}
