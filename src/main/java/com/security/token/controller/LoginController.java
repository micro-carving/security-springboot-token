package com.security.token.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.security.token.annotation.AuthToken;
import com.security.token.annotation.PassToken;
import com.security.token.entity.User;
import com.security.token.entity.UserAuthenticationRequest;
import com.security.token.service.AuthenticationService;
import com.security.token.utils.TokenGenerator;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/25
 */
@RestController
public class LoginController {

    @Resource
    AuthenticationService authenticationService;
    @Resource
    TokenGenerator<User> tokenGenerator;

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
        Map<String, Object> authenticationMap = authenticationService.userAuthentication(userAuthenticationRequest);
        User user = (User) authenticationMap.get("userData");
        // 生成token
        String token = tokenGenerator.getTokenByJwt(user);
        map.put("token", token);
        map.put("message", user.getUserName() + "登录成功！");
        return JSONUtil.toJsonStr(map);
    }

    /**
     * 资源 r1
     *
     * @param token ：request对象
     * @return {String}
     */
    @AuthToken
    @GetMapping(value = "/r/r1")
    public String r1(@RequestParam String token) {
        String userName;
        if (StrUtil.isEmpty(token)) {
            userName = "匿名";
        } else {
            userName = JWT.decode(token).getClaim("userName").asString();
        }
        return userName + "访问资源r1";
    }


    /**
     * 显示通过验证的信息
     *
     * @return {String}
     */
    @PassToken(required = false)
    @GetMapping("/getMessage")
    public String getMessage(@RequestParam String token, UserAuthenticationRequest userAuthenticationRequest) {
        final String userName = JWT.decode(token).getClaim("userName").asString();
        String userName1 = userAuthenticationRequest.getUserName();
        if (StrUtil.equals(userName, userName1)) {
            return userName + " 已通过验证";
        }
        return "验证未通过";
    }
}
