package com.spring.beans.factory.config;

import com.spring.beans.factory.BeanFactory;

/**
 * 自动注入bean工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;

    Object applyBeanPostProcessorBeforeInitialization(Object singleton, String beanName);

    Object applyBeanPostProcessorAfterInitialization(Object singleton, String beanName);

}
