package com.spring.beans;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Bean包装类
 *
 * @author zhenxingchen4
 * @since 2025/5/7
 */
public class BeanWrapperImpl extends PropertyEditorRegistrySupport {
    private Object wrapperObject;
    private Class<?> clazz;
    PropertyValues pvs;

    public BeanWrapperImpl(Object wrapperObject) {
        registerDefaultEditors();
        this.wrapperObject = wrapperObject;
        this.clazz = wrapperObject.getClass();
    }

    public void setBeanInstance(Object wrapperObject) {
        this.wrapperObject = wrapperObject;
    }

    public Object getBeanInstance() {
        return wrapperObject;
    }

    public void setPropertyValues(PropertyValues pvs) {
        this.pvs = pvs;
        for (PropertyValue pv : this.pvs.getPropertyValues()) {
            setPropertyValue(pv);
        }
    }

    private void setPropertyValue(PropertyValue pv) {
        BeanPropertyHandler propertyHandler = new BeanPropertyHandler(pv.getName());
        PropertyEditor propertyEditor = getCustomEditor(propertyHandler.getPropertyClazz());
        if (propertyEditor == null) {
            propertyEditor = this.getDefaultEditor(propertyHandler.getPropertyClazz());
        }

        propertyEditor.setAsText((String) pv.getValue());
        propertyHandler.setValue(propertyEditor.getValue());
    }

    class BeanPropertyHandler {
        Method writeMethod ;
        Method readMethod;
        Class<?> propertyClazz;

        public BeanPropertyHandler(String propertyName) {
            try {
                Field filed = clazz.getDeclaredField(propertyName);
                propertyClazz = filed.getType();

                this.writeMethod = clazz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClazz);
                this.readMethod = clazz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));

            } catch (NoSuchFieldException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        public Class<?> getPropertyClazz() {
            return propertyClazz;
        }

        public Object getValue() {
            Object result;
            writeMethod.setAccessible(true);

            try {
                result = readMethod.invoke(wrapperObject);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            return result;
        }

        public void setValue(Object value) {
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrapperObject, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
