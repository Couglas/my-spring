package definition;

import factory.DefaultUserFactory;
import factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * bean 初始化demo
 *
 * @author couglas
 * @since 2024/5/18
 */
public class SingletonBeanDefinitionDemo {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.refresh();

        UserFactory userFactory = new DefaultUserFactory();
        SingletonBeanRegistry singletonBeanRegistry = annotationConfigApplicationContext.getBeanFactory();
        singletonBeanRegistry.registerSingleton("userFactory", userFactory);

        UserFactory userFactory1 = annotationConfigApplicationContext.getBean("userFactory", UserFactory.class);
        System.out.println(userFactory1 == userFactory);


        annotationConfigApplicationContext.close();


    }

}
