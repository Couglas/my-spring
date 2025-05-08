package com.test;

import com.spring.web.bind.WebDataBinder;
import com.spring.web.bind.support.WebBindingInitializer;

import java.util.Date;

/**
 * 日期初始化器
 *
 * @author zhenxingchen4
 * @since 2025/5/7
 */
public class DateInitializer implements WebBindingInitializer {
    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class, "yyyy-MM-dd", false));
    }
}
