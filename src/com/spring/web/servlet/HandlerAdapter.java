package com.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 适配处理
 *
 * @author zhenxingchen4
 * @since 2025/5/6
 */
public interface HandlerAdapter {
    ModelAndView handle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception;
}
