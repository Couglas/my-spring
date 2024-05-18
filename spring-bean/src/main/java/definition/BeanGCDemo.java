package definition;

import factory.DefaultUserFactory;
import factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bean 初始化demo
 *
 * @author couglas
 * @since 2024/5/18
 */
public class BeanGCDemo {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(BeanGCDemo.class);
        annotationConfigApplicationContext.refresh();
        UserFactory bean = annotationConfigApplicationContext.getBean(UserFactory.class);
        System.out.println(bean);
        annotationConfigApplicationContext.close();
        Thread.sleep(10000l);
        System.gc();
        Thread.sleep(1000l);
    }


    @Bean(initMethod = "customerInit", destroyMethod = "destroyFactory")
//    @Lazy
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }
}
