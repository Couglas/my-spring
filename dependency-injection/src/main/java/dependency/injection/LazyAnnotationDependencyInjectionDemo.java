package dependency.injection;

import dependency.injection.annotation.UserGroup;
import domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

/**
 * 延迟依赖注入示例
 *
 * @author couglas
 * @since 2024/5/21
 */
public class LazyAnnotationDependencyInjectionDemo {
    @Autowired
    private User user;

    @Autowired
    private ObjectProvider<User> objectProvider;

    @Autowired
    private ObjectFactory<User> objectFactory;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(LazyAnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        applicationContext.refresh();
        LazyAnnotationDependencyInjectionDemo demo = applicationContext.getBean(LazyAnnotationDependencyInjectionDemo.class);

//        System.out.println(demo.user);
//        System.out.println(demo.objectProvider.getObject());
//        demo.objectProvider.stream().forEach(System.out::println);
        System.out.println(demo.objectFactory.getObject());

        applicationContext.close();
    }
}
