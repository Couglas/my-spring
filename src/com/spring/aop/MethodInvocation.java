package com.spring.aop;

import java.lang.reflect.Method;

/**
 * 方法调用
 *
 * @author zhenxingchen4
 * @since 2025/5/13
 */
public interface MethodInvocation {
    Method getMethod();

    Object[] getArguments();

    Object getThis();

    Object proceed() throws Throwable;
}
