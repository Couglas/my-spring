package com.spring.web.context.support;

import com.spring.beans.BeanDefinition;
import com.spring.beans.BeanException;
import com.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import com.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.beans.factory.support.DefaultListableBeanFactory;
import com.spring.context.AbstractApplicationContext;
import com.spring.context.ApplicationListener;
import com.spring.context.SimpleApplicationEventPublisher;
import com.spring.context.WebContextRefreshEvent;
import com.spring.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 注解配置web应用上下文
 *
 * @author zhenxingchen4
 * @since 2025/4/30
 */
public class AnnotationConfigWebApplicationContext extends AbstractApplicationContext implements WebApplicationContext {
    private WebApplicationContext parentApplicationContext;
    private ServletContext servletContext;
    private DefaultListableBeanFactory beanFactory;
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();

    public AnnotationConfigWebApplicationContext(String fileName) {
        this(fileName, null);
    }

    public AnnotationConfigWebApplicationContext(String fileName, WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        this.servletContext = this.parentApplicationContext.getServletContext();

        URL xmlPath;
        try {
            xmlPath = this.getServletContext().getResource(fileName);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        List<String> packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        List<String> controllerNames = scanPackages(packageNames);
        this.beanFactory = new DefaultListableBeanFactory();
        this.beanFactory.setParentBeanFactory(this.parentApplicationContext.getBeanFactory());
        loadBeanDefinitions(controllerNames);

        try {
            refresh();
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadBeanDefinitions(List<String> controllerNames) {
        for (String controllerName : controllerNames) {
            BeanDefinition beanDefinition = new BeanDefinition(controllerName, controllerName);
            this.beanFactory.registerBeanDefinition(controllerName, beanDefinition);
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

    public void setParent(WebApplicationContext parentApplicationContext) {
        this.parentApplicationContext = parentApplicationContext;
        this.beanFactory.setParentBeanFactory(this.parentApplicationContext.getBeanFactory());
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    @Override
    public ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }

    @Override
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    @Override
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor());
    }

    @Override
    protected void initApplicationEventPublisher() {
        this.setApplicationEventPublisher(new SimpleApplicationEventPublisher());
    }

    @Override
    protected void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    protected void registerListeners() {
        this.getApplicationEventPublisher().addApplicationListener(new ApplicationListener());
    }

    @Override
    protected void finishRefresh() {
        publishEvent(new WebContextRefreshEvent("web application context refreshed!"));
    }

}
