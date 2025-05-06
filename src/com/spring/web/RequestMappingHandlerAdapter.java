package com.spring.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 请求映射处理适配器
 *
 * @author zhenxingchen4
 * @since 2025/5/6
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    private WebApplicationContext webApplicationContext;

    public RequestMappingHandlerAdapter(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        handleInternal(req, resp, (HandlerMethod) handler);
    }

    private void handleInternal(HttpServletRequest req, HttpServletResponse resp, HandlerMethod handler) {
        if (handler == null) {
            return;
        }

        Method method = handler.getMethod();
        Object bean = handler.getBean();
        try {
            Object result = method.invoke(bean);
            resp.getWriter().append(result.toString());
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
