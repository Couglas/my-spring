package com.spring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * view接口
 *
 * @author zhenxingchen4
 * @since 2025/5/8
 */
public interface View {
    void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception;

    default String getContentType() {
        return null;
    }

    void setContentType(String contentType);

    void setUrl(String url);

    String getUrl();

    void setRequestContextAttribute(String requestContextAttribute);

    String getRequestContextAttribute();
}
