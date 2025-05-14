package com.spring.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}
