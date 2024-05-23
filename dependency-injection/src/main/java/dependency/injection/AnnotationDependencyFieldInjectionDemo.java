package dependency.injection;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 注解依赖field注入示例
 *
 * @author couglas
 * @since 2024/5/21
 */
public class AnnotationDependencyFieldInjectionDemo {

    @Autowired
//    private static UserHolder userHolder;
    private UserHolder userHolder;
    @Resource
    private UserHolder userHolder1;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationDependencyFieldInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        applicationContext.refresh();
        AnnotationDependencyFieldInjectionDemo demo = applicationContext.getBean(AnnotationDependencyFieldInjectionDemo.class);

//        UserHolder userHolder1 = demo.userHolder;
        UserHolder userHolder1 = demo.userHolder1;
        System.out.println(userHolder1);
        System.out.println(demo.userHolder == userHolder1);

        applicationContext.close();
    }

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }
}
