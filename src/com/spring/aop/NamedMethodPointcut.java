package com.spring.aop;

import com.spring.util.PatternMatchUtils;

import java.lang.reflect.Method;

/**
 * 名称匹配切点
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public class NamedMethodPointcut implements MethodMatcher, Pointcut {
    private String mappedName;

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return mappedName.equals(method.getName()) || isMatch(method.getName(), mappedName);
    }

    private boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
