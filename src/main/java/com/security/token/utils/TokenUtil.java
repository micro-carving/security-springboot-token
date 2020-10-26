package com.security.token.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.security.token.dto.UserDto;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : TokenUtil
 * @packageName : com.security.token.utils
 * @description : token工具类
 * @since : 2020/10/26
 */
public class TokenUtil implements TokenGenerator<UserDto> {
    /**
     * 过期时间为15分钟
     */
    private static final long EXPIRE_TIME = 15 * 60 * 1000;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = IdUtil.simpleUUID();
    /**
     * 时间戳
     */
    private final long TIME_STAMP = System.currentTimeMillis();

    /**
     * 通过手动生成token字符串
     *
     * @param strings ：可变字符串参数
     * @return {String}
     */
    @Override
    public String getTokenByManual(String... strings) {

        StringBuilder tokenMeta = new StringBuilder(20);
        for (String s : strings) {
            tokenMeta.append(s).append(TOKEN_SECRET);
        }
        tokenMeta.append(TIME_STAMP);
        // 对字符串进行md5加密
        return DigestUtils.md5DigestAsHex(tokenMeta.toString().getBytes());
    }

    /**
     * 通过JWT生成token
     *
     * @param user ：加密的数据对象
     * @return {String}
     */
    @Override
    public String getTokenByJwt(UserDto user) {
        String token = "";
        // 过期时间
        final Date date = new Date(TIME_STAMP + EXPIRE_TIME);
        // 私钥及加密算法
        final Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        // 设置头部信息
        final Map<String, Object> header = MapUtil.newHashMap();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        // 附带用户名、手机号、权限等信息，生成签名
        token = JWT.create()
                .withHeader(header)
                .withClaim("userName", user.getUserName())
                .withClaim("mobile", user.getMobile())
                .withClaim("authorities", user.getAuthorities().toString())
                .withExpiresAt(date)
                .sign(algorithm);
        return token;
    }
}
