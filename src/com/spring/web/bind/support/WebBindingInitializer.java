package com.spring.web.bind.support;

import com.spring.web.bind.WebDataBinder;

/**
 * web数据转换初始化器
 *
 * @author zhenxingchen4
 * @since 2025/5/7
 */
public interface WebBindingInitializer {
    void initBinder(WebDataBinder binder);
}
