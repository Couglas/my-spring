package com.spring.web;

import com.spring.core.Resource;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * xml配置解析器
 *
 * @author zhenxingchen4
 * @since 2025/4/28
 */
public class XmlConfigReader {
    public Map<String, MappingValue> loadConfig(Resource resource) {
        Map<String, MappingValue> mappings = new HashMap<>();

        while (resource.hasNext()) {
            Element element = (Element) resource.next();
            String id = element.attributeValue("id");
            String clazz = element.attributeValue("class");
            String method = element.attributeValue("value");

            mappings.put(id, new MappingValue(id, clazz, method));
        }

        return mappings;
    }
}
