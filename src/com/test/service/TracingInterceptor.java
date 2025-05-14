package com.test.service;

import com.spring.aop.MethodInterceptor;
import com.spring.aop.MethodInvocation;

/**
 * 跟踪拦截器
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public class TracingInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("this: " + invocation.getThis());
        System.out.println("method: " + invocation.getMethod() + " called with args: " + invocation.getArguments());
        Object result = invocation.proceed();
        System.out.println("method: " + invocation.getMethod() + " return: " + result);

        return result;
    }
}
