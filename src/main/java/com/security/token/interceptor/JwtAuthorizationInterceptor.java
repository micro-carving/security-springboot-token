package com.security.token.interceptor;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.security.token.annotation.AuthToken;
import com.security.token.annotation.PassToken;
import com.security.token.dto.UserDto;
import com.security.token.service.AuthenticationService;
import com.security.token.service.impl.AuthenticationServiceImpl;
import com.security.token.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : JwtAuthorizationInterceptor
 * @packageName : com.security.token.interceptor
 * @description : jwt权限拦截器
 * @since : 2020/10/27
 */
public class JwtAuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    AuthenticationServiceImpl authenticationServiceImpl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 检查是否有PassToken注解，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            final PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 检查是否有AuthToken注解
        if (method.isAnnotationPresent(AuthToken.class)) {
            final AuthToken authToken = method.getAnnotation(AuthToken.class);
            if (authToken.required()) {
                if (StringUtils.isEmpty(token)) {
                    this.responseContent(response, "无token，请登录！", HttpServletResponse.SC_SEE_OTHER);
                    return false;
                }
                try {
                    // 获取 token 中的userName
                    final String userName = JWT.decode(token).getClaim("userName").asString();
                    request.setAttribute(UserDto.REQUEST_USER_KEY, userName);
                    final String requestUri = request.getRequestURI();
                    final UserDto userDto = authenticationServiceImpl.getUserDto(userName);
                    // 验证 token
                    if (TokenUtil.verification(token, userDto)) {
                        // 具有p1权限才能访问r1上的资源，具有p2权限才能访问r2上的资源
                        if (userDto.getAuthorities().contains("p1") && requestUri.contains("/r/r1")) {
                            return true;
                        } else if (userDto.getAuthorities().contains("p2") && requestUri.contains("/r/r2")) {
                            return true;
                        } else {
                            this.responseContent(response, "没有权限，拒绝访问！", HttpServletResponse.SC_UNAUTHORIZED);
                        }
                    }
                } catch (JWTDecodeException e) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 响应输出内容
     *
     * @param response   ：响应
     * @param msg        ：响应信息
     * @param statusCode ：响应状态码
     * @throws IOException ：io异常
     */
    private void responseContent(HttpServletResponse response, String msg, int statusCode) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(statusCode);
        final PrintWriter writer = response.getWriter();
        writer.println(msg);
        writer.close();
    }
}
