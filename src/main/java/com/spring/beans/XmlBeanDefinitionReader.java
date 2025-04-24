package com.spring.beans;

import com.spring.core.Resource;
import org.dom4j.Element;

import java.util.List;

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
            BeanDefinition beanDefinition = new BeanDefinition(id, className);

            PropertyValues propertyValues = new PropertyValues();
            List<Element> propertyElements = element.elements("property");
            for (Element propertyElement : propertyElements) {
                String type = propertyElement.attributeValue("type");
                String name = propertyElement.attributeValue("name");
                String value = propertyElement.attributeValue("value");

                propertyValues.addPropertyValue(new PropertyValue(type, name, value));
            }
            beanDefinition.setPropertyValues(propertyValues);

            List<Element> constructorElements = element.elements("constructor-arg");
            ArgumentValues argumentValues = new ArgumentValues();
            for (Element constructorElement : constructorElements) {
                String type = constructorElement.attributeValue("type");
                String name = constructorElement.attributeValue("name");
                String value = constructorElement.attributeValue("value");

                argumentValues.addArgumentValue(new ArgumentValue(type, name, value));
            }
            beanDefinition.setConstructorArgumentValues(argumentValues);

            this.beanFactory.registerBeanDefinition(id, beanDefinition);
        }
    }
}
