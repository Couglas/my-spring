package com.spring.beans.factory.support;

import com.spring.beans.BeanException;
import com.spring.beans.factory.FactoryBean;

/**
 * 工厂bean注册支持类
 *
 * @author zhenxingchen4
 * @since 2025/5/12
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    protected Class<?> getTypeForFactoryBean(final FactoryBean<?> factoryBean) {
        return factoryBean.getObjectType();
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factoryBean, String beanName) {
        Object object = doGetObjectFromFactoryBean(factoryBean, beanName);

        try {
            object = postProcessObjectFromFactoryBean(object, beanName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return object;
    }

    private Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeanException {
        return object;
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factoryBean, final String beanName) {
        Object object;
        try {
            object = factoryBean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return object;
    }


}
