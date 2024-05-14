package definition;

import domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean 别名示例
 *
 * @author couglas
 * @since 2024/5/14
 */
public class BeanAliasDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/beans-definition-context.xml");
        User newUser = beanFactory.getBean("newUser", User.class);
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user == newUser);
    }
}
