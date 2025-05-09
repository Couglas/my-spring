package com.spring.web.servlet.view;

import com.spring.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 默认view实现类
 *
 * @author zhenxingchen4
 * @since 2025/5/8
 */
public class JstlView implements View {
    private static final String DEFAULT_CONTENT_TYPE = "text/html;charset=ISO-8859-1";
    private String contentType = DEFAULT_CONTENT_TYPE;
    private String requestContextAttribute;
    private String beanName;
    private String url;


    @Override
    public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        for (Map.Entry<String, ?> entry : model.entrySet()) {
            req.setAttribute(entry.getKey(), entry.getValue());
        }
        req.getRequestDispatcher(getUrl()).forward(req, resp);
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public void setRequestContextAttribute(String requestContextAttribute) {
        this.requestContextAttribute = requestContextAttribute;
    }

    @Override
    public String getRequestContextAttribute() {
        return this.requestContextAttribute;
    }
}
