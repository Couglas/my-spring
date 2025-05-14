package com.spring.aop;

import java.lang.reflect.Method;

/**
 * 返回后增强
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public interface AfterReturningAdvice extends AfterAdvice {
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
