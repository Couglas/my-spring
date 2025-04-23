package com.spring.beans;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单bean工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/22
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {
    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    @Override
    public Object getBean(String beanName) throws BeanException {
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (beanDefinition == null) {
                throw new BeanException("no bean found!");
            }
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e ) {
                throw new RuntimeException(e);
            }

            this.registerSingleton(beanName, singleton);
        }
        return singleton;
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public void registerBean(String beanName, Object object) {
        this.registerSingleton(beanName, object);
    }

    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanDefinition.getId(), beanDefinition);
    }
}
