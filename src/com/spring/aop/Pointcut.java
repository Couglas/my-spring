package com.spring.aop;

/**
 * 切点
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();
}
