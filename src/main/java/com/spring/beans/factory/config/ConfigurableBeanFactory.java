package com.spring.beans.factory.config;

import com.spring.beans.factory.BeanFactory;

/**
 * 维护bean依赖关系以及支持bean处理器的工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public interface ConfigurableBeanFactory extends BeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    int getBeanPostProcessorCount();

    void registerDependentBean(String beanName, String dependentBeanName);

    String[] getDependentBeans(String beanName);

    String[] getDependenciesForBean(String beanName);
}
