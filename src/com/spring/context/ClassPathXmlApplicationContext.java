package com.spring.context;

import com.spring.beans.BeanException;
import com.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.beans.factory.support.DefaultListableBeanFactory;
import com.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.spring.core.ClassPathXmlResource;

/**
 * xml应用上下文
 *
 * @author zhenxingchen4
 * @since 2025/4/8
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext  {
    private final DefaultListableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        ClassPathXmlResource resource = new ClassPathXmlResource(fileName);
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        reader.loadBeanDefinitions(resource);
        this.beanFactory = defaultListableBeanFactory;

        if (isRefresh) {
            try {
                refresh();
            } catch (BeanException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    protected void initApplicationEventPublisher() {
        ApplicationEventPublisher publisher = new SimpleApplicationEventPublisher();
        this.setApplicationEventPublisher(publisher);
    }

    @Override
    protected void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    protected void registerListeners() {
        ApplicationListener applicationListener = new ApplicationListener();
        this.getApplicationEventPublisher().addApplicationListener(applicationListener);
    }

    @Override
    protected void finishRefresh() {
        publishEvent(new ContextRefreshEvent("xml context refreshed"));
    }
}
