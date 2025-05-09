package com.spring.web.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回类
 *
 * @author zhenxingchen4
 * @since 2025/5/8
 */
public class ModelAndView {
    private Object view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView() {
    }

    public ModelAndView(String viewName) {
        this.view = viewName;
    }

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView(Object viewName, Map<String, ?> modelData) {
        this.view = viewName;
        addAllAttributes(modelData);
    }

    public ModelAndView(View view, Map<String, ?> model) {
        this.view = view;
        addAllAttributes(model);
    }

    public ModelAndView(String viewName, String modelName, Object modelObject) {
        this.view = viewName;
        addObject(modelName, modelObject);
    }

    public ModelAndView(View view, String modelName, Object modelObject) {
        this.view = view;
        addObject(modelName, modelObject);
    }

    public void setViewName(String viewName) {
        this.view = viewName;
    }

    public String getViewName() {
        return (this.view instanceof String ? (String) this.view : null);
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return (this.view instanceof View ? (View) this.view : null);
    }

    public boolean hasView() {
        return this.view != null;
    }

    public boolean isReference() {
        return (this.view instanceof String);
    }

    public Map<String, Object> getModel() {
        return this.model;
    }

    private void addObject(String modelName, Object modelObject) {
        addAttribute(modelName, modelObject);
    }

    private void addAttribute(String modelName, Object modelObject) {
        model.put(modelName, modelObject);
    }

    private void addAllAttributes(Map<String, ?> modelData) {
        if (modelData != null) {
            model.putAll(modelData);
        }
    }
}
