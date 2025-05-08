package com.spring.web.servlet;

import com.spring.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 映射处理
 *
 * @author zhenxingchen4
 * @since 2025/5/6
 */
public interface HandlerMapping {
    HandlerMethod getHandler(HttpServletRequest req) throws Exception;
}
