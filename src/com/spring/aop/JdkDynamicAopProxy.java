package com.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态aop代理
 *
 * @author zhenxingchen4
 * @since 2025/5/12
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {
    Object target;

    public JdkDynamicAopProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equalsIgnoreCase("doAction")) {
            System.out.println("proxy print");
            return method.invoke(target, args);
        }
        return null;
    }
}
