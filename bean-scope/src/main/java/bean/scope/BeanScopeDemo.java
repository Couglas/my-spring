package bean.scope;

import domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Collection;
import java.util.Map;

/**
 * Bean作用域示例
 *
 * @author couglas
 * @since 2024/5/27
 */
public class BeanScopeDemo implements DisposableBean {

    @Bean
    public static User singletonUser() {
        return createUser();
    }

    @Bean
//    @Scope(scopeName = "prototype")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static User prototypeUser() {
        return createUser();
    }

    private static User createUser() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser;

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser2;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser3;

    @Autowired
    private Map<String, User> users;

    @Autowired
//    private BeanFactory beanFactory;
    private ConfigurableBeanFactory beanFactory;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(BeanScopeDemo.class);


        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
                @Override
                public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                    System.out.printf("%s bean name : %s \n", bean.getClass().getName(), beanName);
                    return bean;
                }
            });
        });

        applicationContext.refresh();

//        scopedBeansLookup(applicationContext);
//        scopedBeansInjection(applicationContext);

        applicationContext.close();
    }


    // 依赖注入prototype每次都是新生成一个，singleton都是同一个。依赖注入集合对象，prototype和singleton均会存在一份
    private static void scopedBeansInjection(AnnotationConfigApplicationContext applicationContext) {
        BeanScopeDemo bean = applicationContext.getBean(BeanScopeDemo.class);
        System.out.println(bean.singletonUser);
        System.out.println(bean.singletonUser1);
        System.out.println(bean.prototypeUser);
        System.out.println(bean.prototypeUser1);
        System.out.println(bean.prototypeUser2);
        System.out.println(bean.prototypeUser3);

        System.out.println(bean.users);
    }

    // 依赖查找singleton都是同一个，prototype每次都是新生成的
    private static void scopedBeansLookup(AnnotationConfigApplicationContext applicationContext) {
        for (int i = 0; i < 3; i++) {
            User singletonUser = applicationContext.getBean("singletonUser", User.class);
            System.out.println(singletonUser);
            User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
            System.out.println(prototypeUser);
        }
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("current bean is destory...");
        this.prototypeUser.destory();
        this.prototypeUser1.destory();
        this.prototypeUser2.destory();
        this.prototypeUser3.destory();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(beanName);
            if (beanDefinition.isPrototype()) {
                User user = entry.getValue();
                user.destory();
            }
        }
        System.out.println("current bean destory complete");
    }
}
