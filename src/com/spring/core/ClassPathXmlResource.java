package com.spring.core;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * xml资源
 *
 * @author zhenxingchen4
 * @since 2025/4/22
 */
public class ClassPathXmlResource implements Resource {
    Document document;
    Element rootElement;
    Iterator<Element> elementIterator;

    public ClassPathXmlResource(String fileName) {
        SAXReader saxReader = new SAXReader();
        URL resource = this.getClass().getClassLoader().getResource(fileName);

        try {
            this.document = saxReader.read(resource);
            this.rootElement = document.getRootElement();
            this.elementIterator = rootElement.elementIterator();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean hasNext() {
        return this.elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return this.elementIterator.next();
    }
}
