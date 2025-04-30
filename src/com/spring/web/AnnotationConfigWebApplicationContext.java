package com.spring.web;

import com.spring.context.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;

/**
 * 注解配置web应用上下文
 *
 * @author zhenxingchen4
 * @since 2025/4/30
 */
public class AnnotationConfigWebApplicationContext extends ClassPathXmlApplicationContext implements WebApplicationContext {
    private ServletContext servletContext;
    public AnnotationConfigWebApplicationContext(String fileName, boolean isRefresh) {
        super(fileName, isRefresh);
    }

    public AnnotationConfigWebApplicationContext(String fileName) {
        super(fileName);
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
