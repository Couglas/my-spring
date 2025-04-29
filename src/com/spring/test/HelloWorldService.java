package com.spring.test;

import com.spring.web.RequestMapping;

/**
 * 测试类
 *
 * @author zhenxingchen4
 * @since 2025/4/28
 */
public class HelloWorldService {
    @RequestMapping("/hello")
    public String doGet() {
        return "hello world！";
    }
}
