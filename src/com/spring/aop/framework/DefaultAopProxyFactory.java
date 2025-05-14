package com.spring.aop.framework;

import com.spring.aop.PointcutAdvisor;

/**
 * 默认aop代理工厂
 *
 * @author zhenxingchen4
 * @since 2025/5/12
 */
public class DefaultAopProxyFactory implements AopProxyFactory {

    @Override
    public AopProxy createAopProxy(Object target, PointcutAdvisor advisor) {
        return new JdkDynamicAopProxy(target, advisor);
    }
}
