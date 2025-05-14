package com.test.service;

import com.spring.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * 后置增强服务
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public class AfterAdviceService implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("after returning called");
    }
}
