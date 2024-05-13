package container;

import domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * {@link BeanFactory} beanfactoryContainerDemo
 *
 * @author couglas
 * @since 2024/5/13
 */
public class BeanFactoryContainerDemo {
    public static void main(String[] args) {
        // 当不需要ApplicationContext的额外特性时，可以直接使用BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String path = "classpath:/META-INF/dependency-lookup-context.xml";
        int beanCount = xmlBeanDefinitionReader.loadBeanDefinitions(path);
        System.out.println(beanCount);

        lookupCollectionByType(beanFactory);
    }

    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beans = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("collection by type: " + beans);
        }
    }
}
