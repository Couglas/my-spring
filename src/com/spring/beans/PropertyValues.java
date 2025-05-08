package com.spring.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 属性类
 *
 * @author zhenxingchen4
 * @since 2025/4/24
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public PropertyValues() {
    }

    public PropertyValues(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            propertyValueList.add(new PropertyValue(entry.getKey(), entry.getValue()));
        }
    }

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

    public PropertyValue[] getPropertyValues() {
        return propertyValueList.toArray(new PropertyValue[0]);
    }

}
