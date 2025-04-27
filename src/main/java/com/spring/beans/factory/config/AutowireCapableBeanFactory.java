package com.spring.beans.factory.config;

import com.spring.beans.BeanException;
import com.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.spring.beans.factory.support.AbstractBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动注入bean工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {
    private final List<AutowiredAnnotationBeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public void addBeanPostProcessor(AutowiredAnnotationBeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public int getBeanPostProcessorCount() {
        return this.beanPostProcessors.size();
    }

    public List<AutowiredAnnotationBeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public Object applyBeanPostProcessorAfterInitialization(Object singleton, String beanName) {
        Object result = singleton;

        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : this.beanPostProcessors) {
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

        for (AutowiredAnnotationBeanPostProcessor beanPostProcessor : this.beanPostProcessors) {
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
