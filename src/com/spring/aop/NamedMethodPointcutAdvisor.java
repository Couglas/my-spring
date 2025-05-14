package com.spring.aop;

/**
 * 名称匹配切点增强
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public class NamedMethodPointcutAdvisor implements PointcutAdvisor {
    private Advice advice;
    private MethodInterceptor methodInterceptor;
    private String mappedName;
    private NamedMethodPointcut pointcut = new NamedMethodPointcut();

    public NamedMethodPointcutAdvisor() {
    }

    public NamedMethodPointcutAdvisor(Advice advice) {
        this.advice = advice;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
        if (advice instanceof BeforeAdvice) {
            setMethodInterceptor(new MethodBeforeAdviceInterceptor((MethodBeforeAdvice) advice));
        } else if (advice instanceof AfterAdvice) {
            setMethodInterceptor(new AfterReturningAdviceInterceptor((AfterReturningAdvice) advice));
        } else if (advice instanceof MethodInterceptor) {
            setMethodInterceptor((MethodInterceptor) advice);
        }
    }

    public String getMappedName() {
        return mappedName;
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
        this.pointcut.setMappedName(mappedName);
    }

    public void setPointcut(NamedMethodPointcut pointcut) {
        this.pointcut = pointcut;
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.methodInterceptor;
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
