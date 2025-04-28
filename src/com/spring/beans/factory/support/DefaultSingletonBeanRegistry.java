package com.spring.beans.factory.support;

import com.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认单例bean注册管理
 *
 * @author zhenxingchen4
 * @since 2025/4/23
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final List<String> beanNames = new ArrayList<>();
    private final Map<String, Object> singletons = new ConcurrentHashMap<>(256);
    @Override
    public void registerSingleton(String beanName, Object singleton) {
        synchronized (singletons) {
            this.singletons.put(beanName, singleton);
            this.beanNames.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletons.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletons.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return this.beanNames.toArray(new String[0]);
    }

    protected void removeSingleton(String beanName) {
        synchronized (singletons) {
            this.singletons.remove(beanName);
            this.beanNames.remove(beanName);
        }
    }
}
