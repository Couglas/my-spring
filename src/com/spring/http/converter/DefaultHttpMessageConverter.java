package com.spring.http.converter;

import com.spring.util.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 默认http消息转换器
 *
 * @author zhenxingchen4
 * @since 2025/5/8
 */
public class DefaultHttpMessageConverter implements HttpMessageConverter {
    private final String defaultContentType = "text/json;charset=UTF-8";
    private final String defaultCharacterEncoding = "UTF-8";
    private ObjectMapper objectMapper;

    @Override
    public void write(Object object, HttpServletResponse resp) throws IOException {
        resp.setContentType(defaultContentType);
        resp.setCharacterEncoding(defaultCharacterEncoding);
        writeInternal(object, resp);
        resp.flushBuffer();
    }

    private void writeInternal(Object object, HttpServletResponse resp) throws IOException {
        String jsonStr = this.objectMapper.writeValuesAsString(object);
        resp.getWriter().write(jsonStr);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
