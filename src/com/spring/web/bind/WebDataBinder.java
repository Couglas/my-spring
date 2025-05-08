package com.spring.web.bind;

import com.spring.beans.BeanWrapperImpl;
import com.spring.beans.PropertyEditor;
import com.spring.beans.PropertyValues;
import com.spring.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * web参数绑定
 *
 * @author zhenxingchen4
 * @since 2025/5/7
 */
public class WebDataBinder {
    private Object target;
    private Class<?> clazz;
    private String objectName;
    private BeanWrapperImpl propertyAccessor;

    public WebDataBinder(Object target) {
        this(target, "");
    }

    public WebDataBinder(Object target, String objectName) {
        this.target = target;
        this.objectName = objectName;
        this.clazz = this.target.getClass();
        this.propertyAccessor = new BeanWrapperImpl(target);
    }

    public void bind(HttpServletRequest req) {
        PropertyValues pvs = assignParameters(req);
        addBindValues(pvs, req);
        doBind(pvs);
    }

    private void doBind(PropertyValues pvs) {
        applyPropertyValues(pvs);
    }

    private void applyPropertyValues(PropertyValues pvs) {
        getPropertyAccessor().setPropertyValues(pvs);
    }

    private BeanWrapperImpl getPropertyAccessor() {
        return this.propertyAccessor;
    }

    private void addBindValues(PropertyValues pvs, HttpServletRequest req) {

    }

    private PropertyValues assignParameters(HttpServletRequest req) {
        Map<String, Object> map = WebUtils.getParametersStartWith(req, "");
        return new PropertyValues(map);
    }

    public void registerCustomEditor(Class<?> requiredType, PropertyEditor propertyEditor) {
        getPropertyAccessor().registerCustomEditor(requiredType, propertyEditor);
    }
}
