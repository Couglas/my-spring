package definition;

import domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean实例化demo
 *
 * @author couglas
 * @since 2024/5/16
 */
public class BeanInstatiationDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/beans-instantiation-context.xml");
        User user = beanFactory.getBean("byStaticFactory", User.class);
        User user1 = beanFactory.getBean("byInstanceMethod", User.class);
        User user2 = beanFactory.getBean("byFactoryBean", User.class);
        System.out.println(user);
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user1 == user);
        System.out.println(user1 == user2);
    }
}
