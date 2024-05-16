package definition;

import factory.DefaultUserFactory;
import factory.UserFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Special Bean实例化demo
 *
 * @author couglas
 * @since 2024/5/16
 */
public class SpecialBeanInstatiationDemo {
    public static void main(String[] args) {
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/beans-special-instantiation-context.xml");
        AutowireCapableBeanFactory autowireCapableBeanFactory = beanFactory.getAutowireCapableBeanFactory();
        DefaultUserFactory bean = autowireCapableBeanFactory.createBean(DefaultUserFactory.class);
        System.out.println(bean.createUser());
        serviceLoaderDemo();

        ServiceLoader byServiceLoader = beanFactory.getBean("byServiceLoader", ServiceLoader.class);
        echoServiceLoader(byServiceLoader);
    }

    public static void serviceLoaderDemo() {
        ServiceLoader<UserFactory> load = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        echoServiceLoader(load);
    }

    public static void echoServiceLoader(ServiceLoader<UserFactory> load) {
        Iterator<UserFactory> iterator = load.iterator();
        while (iterator.hasNext()) {
            UserFactory next = iterator.next();
            System.out.println(next.createUser());
        }
    }
}
