package com.spring.aop;

/**
 * 返回后增强拦截器
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public class AfterReturningAdviceInterceptor implements AfterAdvice, MethodInterceptor {
    private final AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = invocation.proceed();
        this.advice.afterReturning(result, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return result;
    }
}
