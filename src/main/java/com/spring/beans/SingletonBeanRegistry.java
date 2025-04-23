package com.spring.beans;

/**
 * 单例bean注册管理
 *
 * @author zhenxingchen4
 * @since 2025/4/23
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singleton);

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

    String[] getSingletonNames();
}
