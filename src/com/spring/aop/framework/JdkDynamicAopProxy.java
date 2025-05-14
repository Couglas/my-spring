package com.spring.aop.framework;

import com.spring.aop.Advisor;
import com.spring.aop.MethodInterceptor;
import com.spring.aop.MethodInvocation;

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
    private Object target;
    private Advisor advisor;

    public JdkDynamicAopProxy(Object target, Advisor advisor) {
        this.target = target;
        this.advisor = advisor;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(JdkDynamicAopProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equalsIgnoreCase("doAction")) {
            Class<?> targetClass = (target != null ? target.getClass() : null);
            MethodInterceptor interceptor = this.advisor.getMethodInterceptor();
            MethodInvocation invocation = new ReflectionMethodInvocation(proxy, target, method, args, targetClass);
            return interceptor.invoke(invocation);
        }
        return null;
    }
}
