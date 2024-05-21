package dependency.injection;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * xml依赖setter注入demo
 *
 * @author couglas
 * @since 2024/5/21
 */
public class XmlDependencySetterInjectionDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

        String path = "classpath:/META-INF/dependency-setter-injection-context.xml";
        beanDefinitionReader.loadBeanDefinitions(path);

        UserHolder bean = beanFactory.getBean(UserHolder.class);
        System.out.println(bean);
    }
}
