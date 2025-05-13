package com.spring.beans.factory;

/**
 * 工厂bean
 *
 * @author zhenxingchen4
 * @since 2025/5/12
 */
public interface FactoryBean<T> {
    T getObject() throws Exception;

    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }
}
