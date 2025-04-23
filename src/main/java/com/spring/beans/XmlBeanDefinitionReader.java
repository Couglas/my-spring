package com.spring.beans;

import com.spring.core.Resource;
import org.dom4j.Element;

/**
 * xml bean解析类
 *
 * @author zhenxingchen4
 * @since 2025/4/22
 */
public class XmlBeanDefinitionReader {
    private final SimpleBeanFactory beanFactory;

    public XmlBeanDefinitionReader(SimpleBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Resource resource) {
        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String id = element.attributeValue("id");
            String className = element.attributeValue("class");
            this.beanFactory.registerBeanDefinition(new BeanDefinition(id, className));
        }
    }
}
