package com.spring.web.method.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 映射注册类
 *
 * @author zhenxingchen4
 * @since 2025/5/6
 */
public class MappingRegistry {
    private List<String> urlMappingNames = new ArrayList<>();
    private Map<String, Object> mappingObjects = new HashMap<>();
    private Map<String, Method> mappingMethods = new HashMap<>();

    public List<String> getUrlMappingNames() {
        return urlMappingNames;
    }

    public void setUrlMappingNames(List<String> urlMappingNames) {
        this.urlMappingNames = urlMappingNames;
    }

    public Map<String, Object> getMappingObjects() {
        return mappingObjects;
    }

    public void setMappingObjects(Map<String, Object> mappingObjects) {
        this.mappingObjects = mappingObjects;
    }

    public Map<String, Method> getMappingMethods() {
        return mappingMethods;
    }

    public void setMappingMethods(Map<String, Method> mappingMethods) {
        this.mappingMethods = mappingMethods;
    }
}
