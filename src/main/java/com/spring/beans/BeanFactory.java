package com.spring.beans;

/**
 * bean工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/22
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeanException;

    boolean containsBean(String beanName);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    Class<?> getType(String name);
}
