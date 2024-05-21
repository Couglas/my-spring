package dependency.injection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Api依赖constructor注入示例
 *
 * @author couglas
 * @since 2024/5/21
 */
public class ApiDependencyConstructorInjectionDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        BeanDefinition beanDefinition = createUserHolderBeanDefinition();
        applicationContext.registerBeanDefinition("userHolder", beanDefinition);

        BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        applicationContext.refresh();
        UserHolder bean = applicationContext.getBean(UserHolder.class);
        System.out.println(bean);

        applicationContext.close();
    }

    private static BeanDefinition createUserHolderBeanDefinition() {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        builder.addConstructorArgReference("admin");
        return builder.getBeanDefinition();
    }
}
