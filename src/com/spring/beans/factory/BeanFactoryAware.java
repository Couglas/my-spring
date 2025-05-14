package com.spring.beans.factory;

/**
 * 感知beanFactory接口
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory);
}
