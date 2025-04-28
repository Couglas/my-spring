package com.spring.beans.factory.config;

import com.spring.beans.BeanException;
import com.spring.beans.factory.BeanFactory;

/**
 * bean初始化器
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException;

    Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException;

    void setBeanFactory(BeanFactory beanFactory);
}
