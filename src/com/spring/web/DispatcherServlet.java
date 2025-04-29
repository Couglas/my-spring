package com.spring.web;

import com.spring.core.ClassPathXmlResource;
import com.spring.core.Resource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 分发servlet
 *
 * @author zhenxingchen4
 * @since 2025/4/28
 */
public class DispatcherServlet extends HttpServlet {
    private Map<String, MappingValue> mappingValues;
    private Map<String, Class<?>> mappingClazz = new HashMap<>();
    private Map<String, Object> mappingObjects = new HashMap<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath;

        try {
            xmlPath = this.getServletContext().getResource(contextConfigLocation);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        Resource resource = new ClassPathXmlResource(xmlPath);
        XmlConfigReader reader = new XmlConfigReader();
        mappingValues = reader.loadConfig(resource);
        refresh();
    }

    private void refresh() throws ServletException {
        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
            String id = entry.getKey();
            String className = entry.getValue().getClazz();
            Object object;
            Class<?> clazz;

            try {
                clazz = Class.forName(className);
                object = clazz.newInstance();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new ServletException(e);
            }

            mappingClazz.put(id, clazz);
            mappingObjects.put(id, object);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (this.mappingValues.get(servletPath) == null) {
            return;
        }

        Class<?> clazz = this.mappingClazz.get(servletPath);
        Object object = this.mappingObjects.get(servletPath);
        String methodName = this.mappingValues.get(servletPath).getMethod();

        Object result;
        try {
            Method method = clazz.getMethod(methodName);
            result = method.invoke(object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new ServletException(e);
        }

        resp.getWriter().append(result.toString());
    }
}
