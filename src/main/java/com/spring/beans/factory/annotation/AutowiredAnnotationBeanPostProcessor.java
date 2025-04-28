package com.spring.beans.factory.annotation;

import com.spring.beans.BeanException;
import com.spring.beans.factory.BeanFactory;
import com.spring.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

/**
 * autowired注解bean初始化器
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        Object result = bean;
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            boolean isAutowired = field.isAnnotationPresent(Autowired.class);
            if (isAutowired) {
                String filedName = field.getName();
                Object autowireObj = this.beanFactory.getBean(filedName);

                try {
                    field.setAccessible(true);
                    field.set(bean, autowireObj);
                    System.out.println("autowire " + filedName + " for bean: " + beanName);
                } catch (IllegalAccessException e) {
                    throw new BeanException(e.getMessage());
                }

            }
        }

        return result;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return null;
    }
}
