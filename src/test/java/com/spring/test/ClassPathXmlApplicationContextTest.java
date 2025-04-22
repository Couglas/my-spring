package com.spring.test;

import com.spring.beans.BeanException;
import com.spring.context.ClassPathXmlApplicationContext;

/**
 * xml解析测试类
 *
 * @author zhenxingchen4
 * @since 2025/4/8
 */
public class ClassPathXmlApplicationContextTest {
    public static void main(String[] args) throws BeanException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        MyFirstBean myFirstBean = (MyFirstBean) applicationContext.getBean("myFirstBean");
        myFirstBean.print();
    }
}
