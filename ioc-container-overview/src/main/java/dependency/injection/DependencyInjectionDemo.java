package dependency.injection;

import domain.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 * 依赖注入demo
 *
 * @author couglas
 * @since 2024/5/13
 */
public class DependencyInjectionDemo {
    public static void main(String[] args) {
        // BeanFactory是底层的IoC容器，ApplicationContext是在它的基础上增加了一些特性，是它的的一个完整的超集，即BeanFactory有的ApplicationContext都有，而且还有
        // 更好整合AOP特性、处理国际化相关、事件发布、WebApplicationContext相关的特性。ApplicationContext和BeanFactory是同一类事物，只不过在底层实现的时候，
        // ApplicationContext在底层组合了BeanFactory的实现
//        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        ApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        UserRepository userRepository = beanFactory.getBean("userRepository", UserRepository.class);
        // 自定义bean
        System.out.println(userRepository.getBeanFactory() == beanFactory);
        System.out.println(userRepository.getUsers());
        // 容器内建依赖
        System.out.println(userRepository.getUserObjectFactory().getObject());
        System.out.println(userRepository.getObjectFactory().getObject());
        System.out.println(userRepository.getObjectFactory().getObject() == beanFactory);
        // 容器内建bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println(environment);

        // 依赖注入
        System.out.println(userRepository.getBeanFactory());
        // 依赖查找
        System.out.println(beanFactory.getBean(BeanFactory.class));

    }
}
