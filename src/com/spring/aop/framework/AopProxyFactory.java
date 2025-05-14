package com.spring.aop.framework;

import com.spring.aop.PointcutAdvisor;

/**
 * aop代理工厂
 *
 * @author zhenxingchen4
 * @since 2025/5/12
 */
public interface AopProxyFactory {
    AopProxy createAopProxy(Object target, PointcutAdvisor advisor);
}
