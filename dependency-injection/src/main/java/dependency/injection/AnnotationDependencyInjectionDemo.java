package dependency.injection;

import domain.User;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 注解依赖注入示例
 *
 * @author couglas
 * @since 2024/5/21
 */
public class AnnotationDependencyInjectionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationDependencyInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        applicationContext.refresh();
        UserHolder bean = applicationContext.getBean(UserHolder.class);
        System.out.println(bean);

        applicationContext.close();
    }

    @Bean
    public UserHolder userHolder(User user) {
//        return new UserHolder(user);
        UserHolder holder = new UserHolder();
        holder.setUser(user);

        return holder;
    }
}
