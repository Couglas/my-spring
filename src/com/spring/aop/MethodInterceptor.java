package com.spring.aop;

/**
 * 方法拦截器，具体方法的增强
 *
 * @author zhenxingchen4
 * @since 2025/5/13
 */
public interface MethodInterceptor extends Interceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;

}
