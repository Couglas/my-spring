package com.spring.beans.factory.config;

import com.spring.beans.BeanException;
import com.spring.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动注入工厂抽象实现类
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory  {
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object singleton, String beanName) {
        Object result = singleton;

        for (BeanPostProcessor beanPostProcessor : this.beanPostProcessors) {
            beanPostProcessor.setBeanFactory(this);
            try {
                result = beanPostProcessor.postProcessAfterInitialization(result, beanName);
            } catch (BeanException e) {
                throw new RuntimeException(e);
            }
        }


        return result;
    }

    @Override
    public Object applyBeanPostProcessorBeforeInitialization(Object singleton, String beanName) {
        Object result = singleton;

        for (BeanPostProcessor beanPostProcessor : this.beanPostProcessors) {
            beanPostProcessor.setBeanFactory(this);
            try {
                result = beanPostProcessor.postProcessBeforeInitialization(result, beanName);
            } catch (BeanException e) {
                throw new RuntimeException(e);
            }
        }


        return result;
    }
}
