package com.spring.aop.framework;

import com.spring.aop.Advisor;
import com.spring.aop.framework.AopProxy;

/**
 * aop代理工厂
 *
 * @author zhenxingchen4
 * @since 2025/5/12
 */
public interface AopProxyFactory {
    AopProxy createAopProxy(Object target, Advisor advisor);
}
