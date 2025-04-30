package com.spring.web;

import com.spring.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * web应用上下文
 *
 * @author zhenxingchen4
 * @since 2025/4/30
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    ServletContext getServletContext();

    void setServletContext(ServletContext servletContext);
}
