package dependency.injection;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * audowiring依赖注入byname示例
 *
 * @author couglas
 * @since 2024/5/22
 */
public class AutowiringByNameDependencyInjectionDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        String path = "classpath:/META-INF/autowiring-dependency-injection-context.xml";
        beanDefinitionReader.loadBeanDefinitions(path);

        UserHolder bean = beanFactory.getBean(UserHolder.class);
        System.out.println(bean);
    }
}
