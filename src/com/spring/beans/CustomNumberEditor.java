package com.spring.beans;

import com.spring.util.NumberUtils;
import com.spring.util.StringUtils;

import java.text.NumberFormat;

/**
 * 自定义数字转换器
 *
 * @author zhenxingchen4
 * @since 2025/5/7
 */
public class CustomNumberEditor implements PropertyEditor {
    private Class<? extends Number> numberClass;
    private NumberFormat numberFormat;
    private boolean allowEmpty;
    private Object value;

    public CustomNumberEditor(Class<? extends Number> numberClass, boolean allowEmpty) {
        this(numberClass, null, allowEmpty);
    }

    public CustomNumberEditor(Class<? extends Number> numberClass, NumberFormat numberFormat, boolean allowEmpty) {
        this.numberClass = numberClass;
        this.numberFormat = numberFormat;
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else if (this.numberFormat != null) {
            setValue(NumberUtils.parseNumber(text, this.numberClass, this.numberFormat));
        } else {
            setValue(NumberUtils.parseNumber(text, this.numberClass));
        }
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Number) {
            this.value = (NumberUtils.convertNumberToTargetClass((Number) value, this.numberClass));
        } else {
            this.value = value;
        }
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public Object getAsText() {
        Object result = this.value;
        if (result == null) {
            return "";
        }

        if (this.numberFormat != null) {
            return this.numberFormat.format(result);
        } else {
            return result.toString();
        }
    }
}
