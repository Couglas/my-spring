package com.test.service;

import com.spring.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 前置增强服务
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public class BeforeAdviceService implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before advice called");
    }
}
