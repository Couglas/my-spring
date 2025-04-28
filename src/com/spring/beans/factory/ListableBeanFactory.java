package com.spring.beans.factory;

import com.spring.beans.BeanException;

import java.util.Map;

/**
 * 将工厂中的bean作为一个集合来管理的工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public interface ListableBeanFactory extends BeanFactory {
    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException;


}
