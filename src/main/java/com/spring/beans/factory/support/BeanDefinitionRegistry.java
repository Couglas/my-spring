package com.spring.beans.factory.support;

import com.spring.beans.BeanDefinition;

/**
 * beanDefinition注册器
 *
 * @author zhenxingchen4
 * @since 2025/4/24
 */
public interface BeanDefinitionRegistry {
    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

    void removeBeanDefinition(String name);

    BeanDefinition getBeanDefinition(String name);

    boolean containsBeanDefinition(String name);

}
