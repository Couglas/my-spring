package com.spring.beans.factory;

import com.spring.beans.BeanException;

/**
 * bean工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/22
 */
public interface BeanFactory {
    Object getBean(String beanName) throws BeanException;

    boolean containsBean(String beanName);

    boolean isSingleton(String beanName);

    boolean isPrototype(String beanName);

    Class<?> getType(String beanName);
}
