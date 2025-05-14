package com.spring.aop;

/**
 * 方法前置拦截器
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public class MethodBeforeAdviceInterceptor implements BeforeAdvice, MethodInterceptor {
    private final MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
