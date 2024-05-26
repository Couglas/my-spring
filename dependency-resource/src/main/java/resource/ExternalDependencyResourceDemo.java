package resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

/**
 * 外部化依赖来源
 *
 * @author couglas
 * @since 2024/5/26
 */
@Configuration
@PropertySource(value = "classpath:/META-INF/default.properties", encoding = "UTF-8")
public class ExternalDependencyResourceDemo {

    @Value("${user.id:-1}")
    private Long id;

    @Value("${usr.name}")
    private String name;

    @Value(("${user.resource}"))
    private Resource resource;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ExternalDependencyResourceDemo.class);

        applicationContext.refresh();

        ExternalDependencyResourceDemo bean = applicationContext.getBean(ExternalDependencyResourceDemo.class);
        System.out.println(bean.id);
        System.out.println(bean.name);
        System.out.println(bean.resource);

        applicationContext.close();
    }
}
