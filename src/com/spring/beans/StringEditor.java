package com.spring.beans;

/**
 * string转换器
 *
 * @author zhenxingchen4
 * @since 2025/5/7
 */
public class StringEditor implements PropertyEditor {
    private Class<String> stringClass;
    private String stringFormat;
    private boolean allowEmpty;
    private Object value;

    public StringEditor(Class<String> stringClass, boolean allowEmpty) {
        this(stringClass, "", allowEmpty);
    }

    public StringEditor(Class<String> stringClass, String stringFormat, boolean allowEmpty) {
        this.stringClass = stringClass;
        this.stringFormat = stringFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        setValue(text);
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return this.value;

    }

    @Override
    public String getAsText() {
        return value.toString();
    }
}
