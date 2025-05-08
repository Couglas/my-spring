package com.spring.beans;

/**
 * 属性转换接口
 *
 * @author zhenxingchen4
 * @since 2025/5/7
 */
public interface PropertyEditor {
    void setAsText(String text);

    void setValue(Object value);

    Object getValue();

    Object getAsText();
}
