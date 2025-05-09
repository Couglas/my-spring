package com.spring.util;

/**
 * 对象转换器
 *
 * @author zhenxingchen4
 * @since 2025/5/8
 */
public interface ObjectMapper {
    void setDateFormat(String dateFormat);

    void setDecimalFormat(String decimalFormat);

    String writeValuesAsString(Object object);
}
