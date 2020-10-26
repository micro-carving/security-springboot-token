package com.security.token.utils;

import org.springframework.stereotype.Component;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : TokenGenerator
 * @packageName : com.security.token.utils
 * @description : token生成器
 * @since : 2020/10/26
 */
@Component
public interface TokenGenerator<T> {
    /**
     * 通过手动生成token字符串
     *
     * @param strings ：可变字符串参数
     * @return {String}
     */
    public String getTokenByManual(String... strings);

    /**
     * 通过JWT生成token
     *
     * @param encryptData ：加密的数据对象
     * @return {String}
     */
    public String getTokenByJwt(T encryptData);
}
