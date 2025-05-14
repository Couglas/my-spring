package com.spring.aop.framework;

import com.spring.aop.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 反射方法调用
 *
 * @author zhenxingchen4
 * @since 2025/5/13
 */
public class ReflectionMethodInvocation implements MethodInvocation {
    private final Object proxy;
    private final Object target;
    private final Method method;
    private Object[] args;
    private Class<?> targetClass;

    public ReflectionMethodInvocation(Object proxy, Object target, Method method, Object[] args, Class<?> targetClass) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.args = args;
        this.targetClass = targetClass;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public Object proceed() throws Throwable {
        return this.method.invoke(this.target, this.args);
    }
}
