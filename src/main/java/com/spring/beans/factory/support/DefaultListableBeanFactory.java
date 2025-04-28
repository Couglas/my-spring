package com.spring.beans.factory.support;

import com.spring.beans.BeanDefinition;
import com.spring.beans.BeanException;
import com.spring.beans.factory.config.AbstractAutowireCapableBeanFactory;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 容器默认工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory {

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionNames.toArray(new String[0]);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (String beanName : this.beanDefinitionNames) {
            boolean match = false;
            BeanDefinition bd = this.getBeanDefinition(beanName);
            Class<?> clazz = bd.getClass();
            if (type.isAssignableFrom(clazz)) {
                match = true;
            }

            if (match) {
                result.add(beanName);
            }
        }

        return result.toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        String[] beanNames = getBeanNamesForType(type);
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);

        for (String beanName : beanNames) {
            Object bean = getBean(beanName);
            result.put(beanName, (T) bean);
        }

        return result;
    }

    @Override
    public void registerDependentBean(String beanName, String dependentBeanName) {

    }

    @Override
    public String[] getDependentBeans(String beanName) {
        return new String[0];
    }

    @Override
    public String[] getDependenciesForBean(String beanName) {
        return new String[0];
    }
}
