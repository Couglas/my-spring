package com.spring.web.method.annotation;

import com.spring.web.bind.annotation.RequestMapping;
import com.spring.web.context.WebApplicationContext;
import com.spring.web.method.HandlerMethod;
import com.spring.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 请求映射处理器
 *
 * @author zhenxingchen4
 * @since 2025/5/6
 */
public class RequestMappingHandlerMapping implements HandlerMapping {
    private WebApplicationContext webApplicationContext;
    private final MappingRegistry mappingRegistry = new MappingRegistry();

    public RequestMappingHandlerMapping(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
        initMapping();
    }

    private void initMapping() {
        Class<?> clazz;
        Object object;
        for (String controllerName : this.webApplicationContext.getBeanDefinitionNames()) {
            try {
                clazz = Class.forName(controllerName);
                object = clazz.newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                if (isRequestMapping) {
                    String urlMapping = method.getAnnotation(RequestMapping.class).value();
                    this.mappingRegistry.getUrlMappingNames().add(urlMapping);
                    this.mappingRegistry.getMappingObjects().put(urlMapping, object);
                    this.mappingRegistry.getMappingMethods().put(urlMapping, method);
                }
            }
        }

    }

    @Override
    public HandlerMethod getHandler(HttpServletRequest req) throws Exception {
        String servletPath = req.getServletPath();
        if (!this.mappingRegistry.getUrlMappingNames().contains(servletPath)) {
            return null;
        }

        Method method = this.mappingRegistry.getMappingMethods().get(servletPath);
        Object object = this.mappingRegistry.getMappingObjects().get(servletPath);
        return new HandlerMethod(method, object);
    }
}
