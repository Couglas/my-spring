package com.spring.http.converter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * http消息转换器
 *
 * @author zhenxingchen4
 * @since 2025/5/8
 */
public interface HttpMessageConverter {
    void write(Object object, HttpServletResponse resp) throws IOException;
}
