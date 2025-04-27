package com.spring.context;

import com.spring.beans.*;
import com.spring.beans.factory.BeanFactory;
import com.spring.beans.factory.support.SimpleBeanFactory;
import com.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.spring.core.ClassPathXmlResource;

/**
 * xml应用上下文
 *
 * @author zhenxingchen4
 * @since 2025/4/8
 */
public class ClassPathXmlApplicationContext implements BeanFactory, ApplicationEventPublisher {
    private final SimpleBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        ClassPathXmlResource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory simpleBeanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(simpleBeanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = simpleBeanFactory;

        if (isRefresh) {
            this.beanFactory.refresh();
        }
    }

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
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
}
