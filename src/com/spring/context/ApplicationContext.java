package com.spring.context;

import com.spring.beans.BeanException;
import com.spring.beans.factory.ListableBeanFactory;
import com.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.spring.beans.factory.config.ConfigurableBeanFactory;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.core.env.Environment;
import com.spring.core.env.EnvironmentCapable;

/**
 * 应用上下文
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, ConfigurableBeanFactory,
        ApplicationEventPublisher {
    String getApplicationName();

    long getStartupDate();

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh() throws BeanException, IllegalStateException;

    void close();

    boolean isActive();

}
