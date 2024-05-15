package definition;

import domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 注解定义Bean示例
 *
 * @author couglas
 * @since 2024/5/15
 */
//@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
//        annotationConfigApplicationContext.register(Config.class);
//        annotationConfigApplicationContext.register(AnnotationBeanDefinitionDemo.class);

        registerBeanDefinition(annotationConfigApplicationContext, "xiaoming");
        registerBeanDefinition(annotationConfigApplicationContext);
//        annotationConfigApplicationContext.register(AnnotationBeanDefinitionDemo.Config.class);
        annotationConfigApplicationContext.refresh();

//        User user = (User) annotationConfigApplicationContext.getBean("user");
//        System.out.println(user);
        System.out.println(annotationConfigApplicationContext.getBeansOfType(Config.class));
//        System.out.println(annotationConfigApplicationContext.getBeansOfType(AnnotationBeanDefinitionDemo.class));
//        System.out.println(annotationConfigApplicationContext.getBeansOfType(AnnotationBeanDefinitionDemo.Config.class));
        System.out.println(annotationConfigApplicationContext.getBeansOfType(User.class));


        annotationConfigApplicationContext.close();
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        builder.addPropertyValue("id", 10L)
                .addPropertyValue("name", "alex");

        if (StringUtils.hasText(beanName)) {
            registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(builder.getBeanDefinition(), registry);
        }
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry) {
        registerBeanDefinition(registry, null);
    }


//    @Component
    public static class Config {
        @Bean(name = {"user", "alias-user"})
        public User user() {
            User user = new User();
            user.setName("couglas");
            user.setId(2L);

            return user;
        }
    }
}
