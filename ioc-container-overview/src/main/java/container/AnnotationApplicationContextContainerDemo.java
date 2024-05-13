package container;

import domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * {@link org.springframework.context.ApplicationContext} ApplicationContextContainerDemo
 *
 * @author couglas
 * @since 2024/5/13
 */
public class AnnotationApplicationContextContainerDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationApplicationContextContainerDemo.class);
        applicationContext.refresh();
        lookupCollectionByType(applicationContext);

        applicationContext.close();
    }
    @Bean
    public User user() {
        User user = new User();
        user.setId(10l);
        user.setName("alex");

        return user;
    }

    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beans = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("collection by type: " + beans);
        }
    }
}
