package com.spring.aop;

/**
 * 支持切点的切面
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
