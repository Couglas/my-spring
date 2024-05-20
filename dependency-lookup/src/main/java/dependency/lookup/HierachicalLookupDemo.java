package dependency.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 层次性依赖查找demo
 *
 * @author couglas
 * @since 2024/5/19
 */
public class HierachicalLookupDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        applicationContext.register();

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        HierarchicalBeanFactory parmentBeanFactory = createBeanFactory();
//        System.out.println(beanFactory.getParentBeanFactory());
        beanFactory.setParentBeanFactory(parmentBeanFactory);
//        System.out.println(beanFactory.getParentBeanFactory());

//        displayLocalBean(beanFactory, "user");
//        displayLocalBean(parmentBeanFactory, "user");

        displayContainsBean(beanFactory, "user");
        displayContainsBean(parmentBeanFactory, "user");

        applicationContext.refresh();
        applicationContext.close();
    }

    public static void displayContainsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("#### beanFactory:%s is contains bean:%s, %s\n", beanFactory, beanName, containsBean(beanFactory, beanName));
    }

    private static boolean containsBean(HierarchicalBeanFactory beanFactory, String beanName) {
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if (parentBeanFactory instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory parentHierarchicalBeanFactory = HierarchicalBeanFactory.class.cast(parentBeanFactory);
            return containsBean(parentHierarchicalBeanFactory, beanName);
        }

        return beanFactory.containsBean(beanName);
    }

    public static void displayLocalBean(HierarchicalBeanFactory beanFactory, String beanName) {
        System.out.printf("beanFactory:%s is contains bean:%s, %s\n", beanFactory, beanName, beanFactory.containsLocalBean(beanName));
    }

    public static ConfigurableListableBeanFactory createBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
        return beanFactory;
    }
}
