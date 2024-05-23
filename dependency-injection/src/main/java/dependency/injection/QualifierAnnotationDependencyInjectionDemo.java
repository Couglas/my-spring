package dependency.injection;

import dependency.injection.annotation.UserGroup;
import domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Qualifier注解依赖注入示例
 *
 * @author couglas
 * @since 2024/5/21
 */
public class QualifierAnnotationDependencyInjectionDemo {
    @Autowired
    private User user;
    @Autowired
    @Qualifier("user")
    private User user1;

    @Autowired
    private Collection<User> users;

    @Autowired
    @Qualifier
    private Collection<User> qualifiedUser;

    @Autowired
    @UserGroup
    private Collection<User> groupUsers;

    @Bean
    @Qualifier
    public User user1() {
        return createUser(5l);
    }

    @Bean
    @Qualifier
    public User user2() {
        return createUser(6l);
    }

    @Bean
    @UserGroup
    public User user3() {
        return createUser(7l);
    }

    @Bean
    @UserGroup
    public User user4() {
        return createUser(8l);
    }

    public static User createUser(long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        applicationContext.refresh();
        QualifierAnnotationDependencyInjectionDemo demo = applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);

//        System.out.println(demo.user);
//        System.out.println(demo.user1);

        System.out.println(demo.users);
        System.out.println(demo.qualifiedUser);
        System.out.println(demo.groupUsers);


        applicationContext.close();
    }
}
