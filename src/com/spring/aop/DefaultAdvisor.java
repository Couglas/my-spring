package com.spring.aop;

/**
 * 默认aop封装接口
 *
 * @author zhenxingchen4
 * @since 2025/5/13
 */
public class DefaultAdvisor implements Advisor {
    private MethodInterceptor methodInterceptor;

    public DefaultAdvisor() {
    }

    @Override
    public MethodInterceptor getMethodInterceptor() {
        return this.methodInterceptor;
    }

    @Override
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }
}
