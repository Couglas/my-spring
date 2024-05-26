package resource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * 依赖来源demo
 *
 * @author couglas
 * @since 2024/5/26
 */
public class DependencyResourceDemo {

    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @PostConstruct
    public void init() {
        System.out.println(beanFactory == applicationContext);
        System.out.println(applicationEventPublisher == resourceLoader);
        System.out.println(applicationContext == applicationEventPublisher);
        System.out.println(beanFactory == applicationContext.getAutowireCapableBeanFactory());
    }

    // 非spring管理的对象，不能用getBean
    @PostConstruct
    public void initByLookup() {
        getBean(BeanFactory.class);
        getBean(ResourceLoader.class);
        getBean(ApplicationContext.class);
        getBean(ApplicationEventPublisher.class);
    }

    private <T> T getBean(Class<T> beanType) {
        try {
            return beanFactory.getBean(beanType);
        } catch (NoSuchBeanDefinitionException e) {
            System.err.println(beanType.getName());
        }
        return null;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(DependencyResourceDemo.class);


        applicationContext.refresh();
        DependencyResourceDemo demo = applicationContext.getBean(DependencyResourceDemo.class);


        applicationContext.close();
    }
}
