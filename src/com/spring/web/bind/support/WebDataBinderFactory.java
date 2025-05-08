package com.spring.web.bind.support;

import com.spring.web.bind.WebDataBinder;

import javax.servlet.http.HttpServletRequest;

/**
 * web数据转换器工厂
 *
 * @author zhenxingchen4
 * @since 2025/5/7
 */
public class WebDataBinderFactory {
    public WebDataBinder createBinder(HttpServletRequest req, Object target, String objectName) {
        WebDataBinder wdb = new WebDataBinder(target, objectName);
        initBinder(wdb, req);
        return wdb;
    }

    private void initBinder(WebDataBinder wdb, HttpServletRequest req) {

    }
}
