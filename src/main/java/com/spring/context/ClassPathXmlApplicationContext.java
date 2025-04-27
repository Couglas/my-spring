package com.spring.context;

import com.spring.beans.BeanException;
import com.spring.beans.factory.BeanFactory;
import com.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.spring.core.ClassPathXmlResource;

import java.util.ArrayList;
import java.util.List;

/**
 * xml应用上下文
 *
 * @author zhenxingchen4
 * @since 2025/4/8
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    private final AutowireCapableBeanFactory beanFactory;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        ClassPathXmlResource resource = new ClassPathXmlResource(fileName);
        AutowireCapableBeanFactory autowireCapableBeanFactory = new AutowireCapableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(autowireCapableBeanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = autowireCapableBeanFactory;

        if (isRefresh) {
            this.refresh();
        }
    }

    private void refresh() {
        registerBeanPostProcessors(this.beanFactory);
        onRefresh();
    }

    private void registerBeanPostProcessors(AutowireCapableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    private void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    public Object getBean(String beanName) throws BeanException {
        return this.beanFactory.getBean(beanName);
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.beanFactory.containsBean(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanFactory.isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanFactory.isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanFactory.getType(name);
    }

    @Override
    public void publishEvent(ApplicationEvent event) {

    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors() {
        return beanFactoryPostProcessors;
    }

    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor beanFactoryPostProcessor) {
        this.beanFactoryPostProcessors.add(beanFactoryPostProcessor);
    }
}
