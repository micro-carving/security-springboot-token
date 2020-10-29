package com.security.token.controller;

import cn.hutool.json.JSONObject;
import com.security.token.annotation.AuthToken;
import com.security.token.constant.ConstantKit;
import com.security.token.entity.ResponseTemplate;
import com.security.token.entity.User;
import com.security.token.mapper.UserMapper;
import com.security.token.utils.TokenGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : WelcomeController
 * @packageName : com.security.token.controller
 * @description : 欢迎页控制器
 * @since : 2020/10/29
 */
@Slf4j
//@RestController
public class WelcomeController {
    @Resource
    TokenGenerator tokenGenerator;

    @Resource
    UserMapper userMapper;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome token authentication";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseTemplate login(String username, String password) {
        log.info("username:" + username + "      password:" + password);
        User user = userMapper.getUser(username, password);
        log.info("user:" + user);
        JSONObject result = new JSONObject();
        if (user != null) {

            Jedis jedis = new Jedis("192.168.1.106", 6379);
            String token = tokenGenerator.getTokenByManual(username, password);
            jedis.set(username, token);
            //设置key生存时间，当key过期时，它会被自动删除，时间是秒
            jedis.expire(username, ConstantKit.TOKEN_EXPIRE_TIME);
            jedis.set(token, username);
            jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
            long currentTime = System.currentTimeMillis();
            jedis.set(token + username, Long.toString(currentTime));
            //用完关闭
            jedis.close();
            result.put("status", "登录成功");
            result.put("token", token);
        } else {
            result.put("status", "登录失败");
        }

        return ResponseTemplate.builder()
                .code(200)
                .message("登录成功")
                .data(result)
                .build();

    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    @AuthToken
    public ResponseTemplate test() {
        log.info("已进入test路径");
        return ResponseTemplate.builder()
                .code(200)
                .message("Success")
                .data("test url")
                .build();
    }
}
