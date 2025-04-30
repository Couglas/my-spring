package com.spring.web;

import com.spring.beans.BeanException;
import com.spring.test.MyFirstBean;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分发servlet
 *
 * @author zhenxingchen4
 * @since 2025/4/28
 */
public class DispatcherServlet extends HttpServlet {
    private List<String> packageNames = new ArrayList<>();
    private Map<String, Object> controllerObjects = new HashMap<>();
    private List<String> controllerNames = new ArrayList<>();
    private Map<String, Class<?>> controllerClasses = new HashMap<>();
    private List<String> urlMappingNames = new ArrayList<>();
    private Map<String, Object> mappingObjects = new HashMap<>();
    private Map<String, Method> mappingMethods = new HashMap<>();
    private WebApplicationContext webApplicationContext;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath;
        try {
            xmlPath = this.getServletContext().getResource(contextConfigLocation);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

        refresh();
    }

    private void refresh() {
        initController();
        initMapping();
    }

    private void initController() {
        this.controllerNames = scanPackages(this.packageNames);
        for (String controllerName : this.controllerNames) {
            Object object;
            Class<?> clazz;

            try {
                clazz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName, clazz);

                object = clazz.newInstance();
                this.controllerObjects.put(controllerName, object);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
    }



    private List<String> scanPackages(List<String> packageNames) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packageNames) {
            tempControllerNames.addAll(scanPackage(packageName));
        }

        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        URI uri;
        try {
            uri = this.getClass().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        File dir = new File(uri);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanPackage(packageName + "." + file.getName());
            } else {
                String controllerName = packageName + "." + file.getName().replace(".class", "");
                tempControllerNames.add(controllerName);
            }
        }

        return tempControllerNames;
    }


    private void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object object = this.controllerObjects.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                boolean isRequestMapping = method.isAnnotationPresent(RequestMapping.class);
                if (isRequestMapping) {
                    String urlMapping = method.getAnnotation(RequestMapping.class).value();
                    this.urlMappingNames.add(urlMapping);
                    this.mappingObjects.put(urlMapping, object);
                    this.mappingMethods.put(urlMapping, method);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (!this.urlMappingNames.contains(servletPath)) {
            return;
        }

        Method method = this.mappingMethods.get(servletPath);
        Object object = this.mappingObjects.get(servletPath);

        Object result;
        try {
            result = method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        MyFirstBean myFirstBean;
        try {
            myFirstBean = (MyFirstBean) webApplicationContext.getBean("myFirstBean");
            myFirstBean.print();

        } catch (BeanException e) {
            throw new RuntimeException(e);
        }

        resp.getWriter().append(result.toString() + "; " + myFirstBean.getCity() + "; " + myFirstBean.getNickname());
    }
}
