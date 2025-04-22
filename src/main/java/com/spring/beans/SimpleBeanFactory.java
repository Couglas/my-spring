package com.spring.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单bean工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/22
 */
public class SimpleBeanFactory implements BeanFactory {
    private final List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private final Map<String, Object> singletons = new HashMap<>();
    private final List<String> beanNames = new ArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeanException {
        Object singleton = singletons.get(beanName);
        if (singleton == null) {
            int index = beanNames.indexOf(beanName);
            if (index == -1) {
                throw new BeanException("no bean found");
            }

            BeanDefinition beanDefinition = beanDefinitions.get(index);
            try {
                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            singletons.put(beanName, singleton);
        }
        return singleton;
    }

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        this.beanDefinitions.add(beanDefinition);
        this.beanNames.add(beanDefinition.getId());
    }
}
