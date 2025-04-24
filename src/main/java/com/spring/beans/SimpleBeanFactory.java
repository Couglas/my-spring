package com.spring.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单bean工厂
 *
 * @author zhenxingchen4
 * @since 2025/4/22
 */
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private final List<String> beanDefinitionNames = new ArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeanException {
        Object singleton = this.getSingleton(beanName);
        if (singleton == null) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition == null) {
                throw new BeanException("no bean found!");
            }
//            try {
//                singleton = Class.forName(beanDefinition.getClassName()).newInstance();
//            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e ) {
//                throw new RuntimeException(e);
//            }
            singleton = createBean(beanDefinition);

            this.registerSingleton(beanName, singleton);
        }
        return singleton;
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.containsSingleton(beanName);
    }

    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }

    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(name, beanDefinition);
        this.beanDefinitionNames.add(name);
        if (!beanDefinition.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeanException e) {
                throw new RuntimeException(e);
            }
        }
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

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clazz;
        Object object;
        Constructor<?> constructor;

        try {
            clazz = Class.forName(beanDefinition.getClassName());
            ArgumentValues argumentValues = beanDefinition.getConstructorArgumentValues();
            if (!argumentValues.isEmpty()) {
                Class<?>[] paramTypes = new Class<?>[argumentValues.getArgumentCount()];
                Object[] paramValues = new Object[argumentValues.getArgumentCount()];
                for (int i = 0; i < argumentValues.getArgumentCount(); i++) {
                    ArgumentValue argumentValue = argumentValues.getIndexedArgument(i);
                    if ("String".equals(argumentValue.getType()) || "java.lang.String".equals(argumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
                    }
                    else if ("Integer".equals(argumentValue.getType()) || "java.lang.Integer".equals(argumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String)argumentValue.getValue());
                    }
                    // 其他的类型加在这，暂时默认都是String
                    else {
                        paramTypes[i] = String.class;
                        paramValues[i] = argumentValue.getValue();
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

        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        if (!propertyValues.isEmpty()) {
            for (int i = 0; i < propertyValues.getPropertyCount(); i++) {
                PropertyValue propertyValue = propertyValues.getIndexedProperty(i);
                String type = propertyValue.getType();
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                Class<?>[] paramTypes = new Class<?>[1];
                Object[] paramValues = new Object[1];
                if ("String".equals(type) || "java.lang.String".equals(type)) {
                    paramTypes[0] = String.class;
                }
                else if ("Integer".equals(type) || "java.lang.Integer".equals(type)) {
                    paramTypes[0] = Integer.class;
                }
                else {
                    paramTypes[0] = String.class;
                }
                paramValues[0] = value;

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

        return object;
    }
}
