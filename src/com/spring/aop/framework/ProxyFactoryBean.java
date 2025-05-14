package com.spring.aop.framework;

import com.spring.aop.*;
import com.spring.beans.BeanException;
import com.spring.beans.factory.BeanFactory;
import com.spring.beans.factory.BeanFactoryAware;
import com.spring.beans.factory.FactoryBean;
import com.spring.util.ClassUtils;

/**
 * 代理factoryBean
 *
 * @author zhenxingchen4
 * @since 2025/5/12
 */
public class ProxyFactoryBean implements FactoryBean<Object>, BeanFactoryAware {
    private AopProxyFactory aopProxyFactory;
    private String[] interceptorNames;
    private String targetName;
    private Object target;
    private ClassLoader proxyClassLoader = ClassUtils.getDefaultClassLoader();
    private Object singletonInstance;
    private String interceptorName;
    private PointcutAdvisor advisor;
    private BeanFactory beanFactory;

    private synchronized void initializeAdvisor() {
        Object advice;
        try {
            advice = this.beanFactory.getBean(this.interceptorName);
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }

        advisor = (PointcutAdvisor) advice;
    }

    public ProxyFactoryBean() {
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public AopProxyFactory getAopProxyFactory() {
        return aopProxyFactory;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        this.aopProxyFactory = aopProxyFactory;
    }

    protected AopProxy createAopProxy() {
        return getAopProxyFactory().createAopProxy(target, this.advisor);
    }

    public void setInterceptorNames(String... interceptorNames) {
        this.interceptorNames = interceptorNames;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public String getInterceptorName() {
        return interceptorName;
    }

    public void setInterceptorName(String interceptorName) {
        this.interceptorName = interceptorName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object getObject() throws Exception {
        initializeAdvisor();
        return getSingletonInstance();
    }

    private synchronized Object getSingletonInstance() {
        if (this.singletonInstance == null) {
            this.singletonInstance = getProxy(createAopProxy());
        }
        return this.singletonInstance;
    }

    private Object getProxy(AopProxy aopProxy) {
        return aopProxy.getProxy();
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

}
