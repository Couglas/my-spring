package com.spring.beans;

/**
 * bean工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/22
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeanException;

    void registerBeanDefinition(BeanDefinition beanDefinition);
}
