package com.spring.beans.factory.config;

/**
 * 构造器参数对象
 *
 * @author zhenxingchen4
 * @since 2025/4/24
 */
public class ConstructorArgumentValue {
    private String type;
    private String name;
    private Object value;

    public ConstructorArgumentValue(String type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
