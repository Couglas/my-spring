package com.spring.web.servlet.view;

import com.spring.web.servlet.View;
import com.spring.web.servlet.ViewResolver;

/**
 * 内部资源view解析器
 *
 * @author zhenxingchen4
 * @since 2025/5/8
 */
public class InternalResourceViewResolver implements ViewResolver {
    private Class<?> viewClass;
    private String viewClassName;
    private String prefix;
    private String suffix;
    private String contentType;

    public InternalResourceViewResolver() {
        if (getViewClass() == null) {
            setViewClass(JstlView.class);
        }
    }

    public Class<?> getViewClass() {
        return viewClass;
    }

    public void setViewClass(Class<?> viewClass) {
        this.viewClass = viewClass;
    }

    public String getViewClassName() {
        return viewClassName;
    }

    public void setViewClassName(String viewClassName) {
        this.viewClassName = viewClassName;
        Class<?> clazz;
        try {
            clazz = Class.forName(viewClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setViewClass(clazz);
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = (prefix != null ? prefix : "");
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = (suffix != null ? suffix : "");
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public View resolveViewName(String viewName) throws Exception {
        return buildView(viewName);
    }

    private View buildView(String viewName)   throws  Exception{
        Class<?> clazz = getViewClass();
        View view = (View) clazz.newInstance();
        view.setUrl(getPrefix() + viewName + getSuffix());
        view.setContentType(getContentType());
        return view;
    }
}
