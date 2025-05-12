package com.spring.batis;

/**
 * sqlSession工厂
 *
 * @author zhenxingchen4
 * @since 2025/5/9
 */
public interface SqlSessionFactory {
    SqlSession openSession();

    MapperNode getMapperNode(String name);


}
