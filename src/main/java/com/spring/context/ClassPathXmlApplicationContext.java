package com.spring.context;

import com.spring.beans.*;
import com.spring.core.ClassPathXmlResource;

/**
 * xml应用上下文
 *
 * @author zhenxingchen4
 * @since 2025/4/8
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    private final BeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        ClassPathXmlResource resource = new ClassPathXmlResource(fileName);
        SimpleBeanFactory simpleBeanFactory = new SimpleBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(simpleBeanFactory);
        reader.loadBeanDefinitions(resource);

        this.beanFactory = simpleBeanFactory;
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
    public void registerBean(String beanName, Object object) {
        this.beanFactory.registerBean(beanName, object);
    }
}
