package com.spring.beans.factory.support;

import com.spring.beans.BeanDefinition;
import com.spring.beans.BeanException;
import com.spring.beans.PropertyValue;
import com.spring.beans.PropertyValues;
import com.spring.beans.factory.BeanFactory;
import com.spring.beans.factory.config.ConstructorArgumentValue;
import com.spring.beans.factory.config.ConstructorArgumentValues;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean工厂基类
 * <p>
 * 除了开始的SimpleBeanFactory和新增的AutowireCapableBeanFactory以及后续可能扩展的beanFactory，
 * 基于专工厂专用的原则，把beanFactory的通用功能提出来，放到这个基类里面，确保基本功能即便子类不实现也
 * 能使用
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry
        implements BeanFactory, BeanDefinitionRegistry {
    protected Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    protected List<String> beanDefinitionNames = new ArrayList<>();
    private final Map<String, Object> earlySingletonObjects = new HashMap<>();

    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (!beanDefinition.isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (BeanException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public Object getBean(String beanName) throws BeanException {
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            singleton = earlySingletonObjects.get(beanName);
            if (singleton == null) {
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition == null) {
                    throw new BeanException("no" + beanName + " bean found!");
                }

                singleton = createBean(beanDefinition);
                this.registerBean(beanName, singleton);
                // 1. 前置处理
                applyBeanPostProcessorBeforeInitialization(singleton, beanName);
                // 2. init-method
                if (beanDefinition.getInitMethodName() != null && !beanDefinition.getInitMethodName().isEmpty()) {
                    invokeInitMethod(beanDefinition, singleton);
                }
                // 3. 后置处理
                applyBeanPostProcessorAfterInitialization(singleton, beanName);

            }
        }

        return singleton;
    }

    private void registerBean(String beanName, Object singleton) {
        this.registerSingleton(beanName, singleton);
    }

    public abstract Object applyBeanPostProcessorAfterInitialization(Object singleton, String beanName);

    public abstract Object applyBeanPostProcessorBeforeInitialization(Object singleton, String beanName);

    private void invokeInitMethod(BeanDefinition beanDefinition, Object object) {
        Class<?> clazz = beanDefinition.getClass();
        Method method;

        try {
            method = clazz.getMethod(beanDefinition.getInitMethodName());
            method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return this.beanDefinitionMap.get(beanName).isSingleton();
    }

    @Override
    public boolean isPrototype(String beanName) {
        return this.beanDefinitionMap.get(beanName).isPrototype();
    }

    @Override
    public Class<?> getType(String beanName) {
        return this.beanDefinitionMap.get(beanName).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
    }

    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);
    }

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    private Object createBean(BeanDefinition bd) {
        Class<?> clazz;
        try {
            clazz = Class.forName(bd.getClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Object object = doCreateBean(bd, clazz);
        this.earlySingletonObjects.put(bd.getId(), object);
        populateBean(bd, clazz, object);

        return object;
    }

    private Object doCreateBean(BeanDefinition bd, Class<?> clazz) {
        Object object;
        Constructor<?> constructor;
        try {
            ConstructorArgumentValues constructorArgumentValues = bd.getConstructorArgumentValues();
            if (!constructorArgumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
                Object[] paramValues = new Object[constructorArgumentValues.getArgumentCount()];
                for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValue argumentValue = constructorArgumentValues.getIndexedArgument(i);
                    String type = argumentValue.getType();
                    Object value = argumentValue.getValue();
                    if ("String".equals(type) || "java.lang.String".equals(type)) {
                        paramTypes[i] = String.class;
                        paramValues[i] = value;
                    } else if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) value);
                    } else {
                        paramTypes[i] = String.class;
                        paramValues[i] = value;
                    }
                }
                constructor = clazz.getConstructor(paramTypes);
                object = constructor.newInstance(paramValues);
            } else {
                object = clazz.newInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("do create bean: " + bd.getId() + ", " + bd.getClassName() + " : " + object);

        return object;
    }

    private void populateBean(BeanDefinition bd, Class<?> clazz, Object object) {
        handleProperties(bd, clazz, object);
    }

    private void handleProperties(BeanDefinition bd, Class<?> clazz, Object object) {
        System.out.println("handle properties bean: " + bd.getId());
        PropertyValues propertyValues = bd.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.getPropertyCount(); i++) {
                PropertyValue propertyValue = propertyValues.getIndexedProperty(i);
                String type = propertyValue.getType();
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if (!isRef) {
                    if ("String".equals(type) || "java.lang.String".equals(type)) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
                        paramTypes[0] = Integer.class;
                    } else {
                        paramTypes[0] = String.class;
                    }
                    paramValues[0] = value;
                } else {
                    try {
                        paramTypes[0] = clazz.forName(type);
                        paramValues[0] = getBean((String) value);
                    } catch (ClassNotFoundException | BeanException e) {
                        throw new RuntimeException(e);
                    }
                }

                String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method;
                try {
                    method = clazz.getMethod(methodName, paramTypes);
                    method.invoke(object, paramValues);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}