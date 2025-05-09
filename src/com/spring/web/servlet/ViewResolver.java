package com.spring.web.servlet;

/**
 * view解析器
 *
 * @author zhenxingchen4
 * @since 2025/5/8
 */
public interface ViewResolver {
    View resolveViewName(String viewName) throws Exception;
}
