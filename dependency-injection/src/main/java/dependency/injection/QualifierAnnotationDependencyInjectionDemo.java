package dependency.injection;

import dependency.injection.annotation.MyAnnotation;
import dependency.injection.annotation.UserGroup;
import domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.*;

import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

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

    @MyAnnotation
    private User myAnnotaionUser;

    // 将多个注入注解都set
//    @Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
//    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
//        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
//        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(Arrays.asList(Autowired.class, MyAnnotation.class));
//        beanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
////        beanPostProcessor.setAutowiredAnnotationType(MyAnnotation.class);
//        return beanPostProcessor;
//    }

    /**
     * 通过order，优先加载。保证Autowired和自定义注解同时存在
     * @return
     */
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 3)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        beanPostProcessor.setAutowiredAnnotationType(MyAnnotation.class);
        return beanPostProcessor;
    }


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

        System.out.println(demo.user);
//        System.out.println(demo.user1);
//
//        System.out.println(demo.users);
//        System.out.println(demo.qualifiedUser);
//        System.out.println(demo.groupUsers);

        System.out.println(demo.myAnnotaionUser);


        applicationContext.close();
    }
}
