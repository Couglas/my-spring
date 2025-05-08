package com.spring.web.method;

import java.lang.reflect.Method;

/**
 * 处理方法
 *
 * @author zhenxingchen4
 * @since 2025/5/6
 */
public class HandlerMethod {
    private Object bean;
    private Class<?> beanType;
    private Method method;
    private Class<?> returnType;
    private String description;
    private String className;
    private String methodName;

    public HandlerMethod(Method method, Object bean) {
        this.method = method;
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
