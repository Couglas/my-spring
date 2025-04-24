package com.spring.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性类
 *
 * @author zhenxingchen4
 * @since 2025/4/24
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();


    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValueList.add(propertyValue);
    }

    public PropertyValue getIndexedProperty(int index) {
        return this.propertyValueList.get(index);
    }

    public int getPropertyCount() {
        return this.propertyValueList.size();
    }

    public boolean isEmpty() {
        return this.propertyValueList.isEmpty();
    }
}
