package com.spring.aop;

import java.lang.reflect.Method;

/**
 * 方法前置增强
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target) throws Throwable;
}
