package com.spring.aop;

/**
 * aop封装接口
 *
 * @author zhenxingchen4
 * @since 2025/5/13
 */
public interface Advisor {
    MethodInterceptor getMethodInterceptor();

    void setMethodInterceptor(MethodInterceptor methodInterceptor);
}
