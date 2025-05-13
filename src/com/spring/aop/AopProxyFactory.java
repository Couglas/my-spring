package com.spring.aop;

/**
 * aop代理工厂
 *
 * @author zhenxingchen4
 * @since 2025/5/12
 */
public interface AopProxyFactory {
    AopProxy createAopProxy(Object target);
}
