package com.security.token.interceptor;

import com.security.token.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : OlinH
 * @version : v1.0
 * @className : AuthenticationInterceptor
 * @packageName : com.security.session.interceptor
 * @description : 身份认证拦截器
 * @since : 2020/10/25
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    /**
     * 拦截器预处理，请求拦截方法
     *
     * @param request  ：请求
     * @param response ：响应
     * @param handler  ：处理器
     * @return {false或者true}
     * @throws Exception ：异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 读取会话信息
        final Object attribute = request.getSession().getAttribute(UserDto.SESSION_USER_KEY);
        if (attribute == null) {
            this.responseContent(response, "请登录！", HttpServletResponse.SC_SEE_OTHER);
            return false;
        }
        final UserDto userDto = (UserDto) attribute;
        // 请求的url
        final String requestUri = request.getRequestURI();
        // 具有p1权限才能访问r1上的资源，具有p2权限才能访问r2上的资源
        if (userDto.getAuthorities().contains("p1") && requestUri.contains("/r/r1")) {
            return true;
        } else if (userDto.getAuthorities().contains("p2") && requestUri.contains("/r/r2")) {
            return true;
        } else {
            this.responseContent(response, "没有权限，拒绝访问！", HttpServletResponse.SC_UNAUTHORIZED);
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
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(statusCode);
        final PrintWriter writer = response.getWriter();
        writer.println(msg);
        writer.close();
    }

}
