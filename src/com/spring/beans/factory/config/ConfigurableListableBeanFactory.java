package com.spring.beans.factory.config;

import com.spring.beans.factory.ListableBeanFactory;

/**
 * 综合beanFactory、listableBeanFactory、configurableBeanFactory、autowireCapableBeanFactory工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, ConfigurableBeanFactory, AutowireCapableBeanFactory {

}
