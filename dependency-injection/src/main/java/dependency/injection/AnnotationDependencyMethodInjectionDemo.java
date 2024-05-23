package dependency.injection;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 注解依赖方法注入示例
 *
 * @author couglas
 * @since 2024/5/21
 */
public class AnnotationDependencyMethodInjectionDemo {

    private UserHolder userHolder;
    private UserHolder userHolder1;

    @Autowired
    private void init1(UserHolder userHolder) {
        this.userHolder = userHolder;
    }

    @Resource
    private void init2(UserHolder userHolder) {
        this.userHolder1 = userHolder;
    }

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationDependencyMethodInjectionDemo.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        applicationContext.refresh();
        AnnotationDependencyMethodInjectionDemo demo = applicationContext.getBean(AnnotationDependencyMethodInjectionDemo.class);
        UserHolder bean = applicationContext.getBean(UserHolder.class);

        UserHolder userHolder1 = demo.userHolder1;
        System.out.println(userHolder1);
        System.out.println(demo.userHolder == userHolder1);
        System.out.println(bean);
        System.out.println(bean == userHolder1);

        applicationContext.close();
    }

}
