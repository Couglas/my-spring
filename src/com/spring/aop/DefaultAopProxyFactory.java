package com.spring.aop;

/**
 * 默认aop代理工厂
 *
 * @author zhenxingchen4
 * @since 2025/5/12
 */
public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(Object target) {
        return new JdkDynamicAopProxy(target);
    }
}
