package com.spring.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 默认对象转换器
 *
 * @author zhenxingchen4
 * @since 2025/5/8
 */
public class DefaultObjectMapper implements ObjectMapper {
    private String dateFormat = "yyyy-MM-dd";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    private String decimalFormat = "#,##0.00";
    private DecimalFormat decimalFormatter = new DecimalFormat(decimalFormat);

    @Override
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public void setDecimalFormat(String decimalFormat) {
        this.decimalFormat = decimalFormat;
        this.decimalFormatter = new DecimalFormat(decimalFormat);
    }

    @Override
    public String writeValuesAsString(Object object) {
        if (object instanceof List) {
            return writeListAsString((List<?>) object);
        }
        String jsonStr = "{";
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String strFiled;
            String strValue;
            Object value;
            field.setAccessible(true);
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            String name = field.getName();
            if (value instanceof Date) {
                LocalDate localDate = ((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                strValue = localDate.format(this.dateTimeFormatter);
            } else if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
                strValue = this.decimalFormatter.format(value);
            } else {
                strValue = value.toString();
            }
            if (jsonStr.equals("{")) {
                strFiled = "\"" + name + "\":\"" + strValue + "\"";
            } else {
                strFiled = ",\"" + name + "\":\"" + strValue + "\"";
            }
            jsonStr += strFiled;
        }
        jsonStr += "}";

        return jsonStr;
    }

    public String writeListAsString(List<?> list) {
        String sJsonStr = "[";

        for (Object obj : list) {
            String sObj = writeValuesAsString(obj);
            if (sJsonStr.equals("[")) {
                sJsonStr += sObj;
            } else {
                sJsonStr += "," + sObj;
            }
        }

        sJsonStr += "]";
        return sJsonStr;

    }
}
