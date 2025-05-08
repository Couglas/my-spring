package com.test.controller;

import com.spring.web.bind.annotation.RequestMapping;
import com.test.SecondBean;

/**
 * 测试类
 *
 * @author zhenxingchen4
 * @since 2025/4/28
 */
public class HelloWorldController {
    @RequestMapping("/hello")
    public String doGet() {
        return "hello world！";
    }

    @RequestMapping("/param")
    public String paramTest(SecondBean secondBean) {
        return secondBean.getDesc();
    }
}
