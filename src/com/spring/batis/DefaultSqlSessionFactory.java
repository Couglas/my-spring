package com.spring.batis;

import com.spring.beans.factory.annotation.Autowired;
import com.spring.jdbc.core.JdbcTemplate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 默认sqlSession工厂
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String mapperLocation;
    private Map<String, MapperNode> mapperNodeMap = new HashMap<>();

    public DefaultSqlSessionFactory() {
    }

    public void init() {
        scanLocation(this.mapperLocation);
    }

    private void scanLocation(String location) {
        String locationPath = this.getClass().getClassLoader().getResource("").getPath() + location;
        File dir = new File(locationPath);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanLocation(location + "/" + file.getName());
            } else {
                buildMapperNodes(location + "/" + file.getName());
            }
        }
    }

    private Map<String, MapperNode> buildMapperNodes(String filePath) {
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(filePath);
        try {
            Document document = saxReader.read(xmlPath);
            Element rootElement = document.getRootElement();
            String namespace = rootElement.attributeValue("namespace");
            Iterator<Element> nodes = rootElement.elementIterator();
            while (nodes.hasNext()) {
                Element node = nodes.next();
                String id = node.attributeValue("id");
                String parameterType = node.attributeValue("parameterType");
                String resultType = node.attributeValue("resultType");
                String sql = node.getText();

                MapperNode mapperNode = new MapperNode();
                mapperNode.setNamespace(namespace);
                mapperNode.setId(id);
                mapperNode.setParameterType(parameterType);
                mapperNode.setResultType(resultType);
                mapperNode.setSql(sql);
                mapperNode.setParameter("");
                this.mapperNodeMap.put(namespace + "." + id, mapperNode);
            }

        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        return mapperNodeMap;
    }

    @Override
    public SqlSession openSession() {
        SqlSession sqlSession = new DefaultSqlSession();
        sqlSession.setJdbcTemplate(jdbcTemplate);
        sqlSession.setSqlSessionFactory(this);
        return sqlSession;
    }

    @Override
    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }

    public String getMapperLocation() {
        return mapperLocation;
    }

    public void setMapperLocation(String mapperLocation) {
        this.mapperLocation = mapperLocation;
    }

    public Map<String, MapperNode> getMapperNodeMap() {
        return mapperNodeMap;
    }
}
