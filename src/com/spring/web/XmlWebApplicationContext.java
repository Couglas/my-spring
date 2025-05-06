package com.spring.web;

import com.spring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * xml web应用上下文
 *
 * @author zhenxingchen4
 * @since 2025/5/6
 */
public class XmlWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {

    private ServletContext servletContext;

    public XmlWebApplicationContext(String fileName) {
        super(fileName);
    }

    public XmlWebApplicationContext(String fileName, boolean isRefresh) {
        super(fileName, isRefresh);
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
