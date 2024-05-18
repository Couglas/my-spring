package definition;

import factory.DefaultUserFactory;
import factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * bean 初始化demo
 *
 * @author couglas
 * @since 2024/5/18
 */
@Configuration
public class BeanInitializingDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(BeanInitializingDemo.class);
        annotationConfigApplicationContext.refresh();
        System.out.println("context finish");
        UserFactory bean = annotationConfigApplicationContext.getBean(UserFactory.class);
        System.out.println(bean);
        System.out.println("context destory prepare");
        annotationConfigApplicationContext.close();
        System.out.println("context destory done");
    }


    @Bean(initMethod = "customerInit", destroyMethod = "destroyFactory")
//    @Lazy
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }
}
