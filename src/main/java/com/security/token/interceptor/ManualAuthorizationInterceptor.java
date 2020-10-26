package com.security.token.interceptor;

import cn.hutool.json.JSONObject;
import com.security.token.annotation.AuthToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : TODO
 * @packageName : com.security.token.interceptor
 * @description : TODO
 * @since : 2020/10/27
 */
@Slf4j
public class ManualAuthorizationInterceptor implements HandlerInterceptor {

    /**
     * redis存储token设置的过期时间，10分钟
     */
    public static final Integer TOKEN_EXPIRE_TIME = 60 * 10;

    /**
     * 设置可以重置token过期时间的时间界限
     */
    public static final Integer TOKEN_RESET_TIME = 1000 * 100;

    /**
     * 鉴权失败后返回的错误信息，默认为401 unauthorized
     */
    private String unauthorizedErrorMessage = "401 unauthorized";

    /**
     * 存放登录用户模型Key的Request Key
     */
    public static final String REQUEST_CURRENT_KEY = "REQUEST_CURRENT_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 如果打上了AuthToken注解则需要验证token
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
            // 存放鉴权信息的Header名称，默认是Authorization
            String httpHeaderName = "Authorization";
            String token = request.getParameter(httpHeaderName);
            log.info("Get token from request is {} ", token);
            String username = "";
            Jedis jedis = new Jedis("172.0.0.1", 6379);
            if (token != null && token.length() != 0) {
                username = jedis.get(token);
                log.info("Get username from Redis is {}", username);
            }
            if (username != null && !"".equals(username.trim())) {
                long tokeBirthTime = Long.parseLong(jedis.get(token + username));
                log.info("token Birth time is: {}", tokeBirthTime);
                Long diff = System.currentTimeMillis() - tokeBirthTime;
                log.info("token is exist : {} ms", diff);
                // 重新设置Redis中的token过期时间
                if (diff > TOKEN_RESET_TIME) {
                    jedis.expire(username, TOKEN_EXPIRE_TIME);
                    jedis.expire(token, TOKEN_EXPIRE_TIME);
                    log.info("Reset expire time success!");
                    long newBirthTime = System.currentTimeMillis();
                    jedis.set(token + username, Long.toString(newBirthTime));
                }
                // 用完关闭
                jedis.close();
                request.setAttribute(REQUEST_CURRENT_KEY, username);
                return true;
            } else {
                JSONObject jsonObject = new JSONObject();

                PrintWriter out = null;
                try {
                    // 鉴权失败后返回的HTTP错误码，默认为401
                    int unauthorizedErrorCode = HttpServletResponse.SC_UNAUTHORIZED;
                    response.setStatus(unauthorizedErrorCode);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    jsonObject.put("code", response.getStatus());
                    jsonObject.put("message", HttpStatus.UNAUTHORIZED);
                    out = response.getWriter();
                    out.println(jsonObject);
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != out) {
                        out.flush();
                        out.close();
                    }
                }

            }

        }
        request.setAttribute(REQUEST_CURRENT_KEY, null);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
