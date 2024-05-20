package dependency.lookup;

import domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 安全依赖查找实例
 *
 * @author couglas
 * @since 2024/5/20
 */
public class SafetyDependencyLookupDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(SafetyDependencyLookupDemo.class);
        applicationContext.refresh();

        // 单一类型
        displayBeanFactoryGetBean(applicationContext);
        displayObjectFactoryGetBean(applicationContext);
        displayObjectProviderIfAvaiable(applicationContext);

        // 集合类型
        displayListableBeanFactoryGetBeansOfType(applicationContext);
        displayObjectProviderStreamOps(applicationContext);

        applicationContext.close();
    }

    // 安全
    private static void displayObjectProviderStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
        printBeanException("displayObjectProviderStreamOps", () -> beanProvider.stream().forEach(System.out::println));

    }

    // 安全
    private static void displayListableBeanFactoryGetBeansOfType(ListableBeanFactory beanFactory) {
        printBeanException("displayListableBeanFactoryGetBeansOfType", () -> beanFactory.getBeansOfType(User.class));
    }

    // 安全
    private static void displayObjectProviderIfAvaiable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
        printBeanException("displayObjectProviderIfAvaiable", beanProvider::getIfAvailable);
    }

    // 非安全
    private static void displayObjectFactoryGetBean(AnnotationConfigApplicationContext applicationContext) {
        ObjectFactory<User> beanProvider = applicationContext.getBeanProvider(User.class);
        printBeanException("displayObjectFactoryGetBean", beanProvider::getObject);
    }

    // 非安全
    public static void displayBeanFactoryGetBean(BeanFactory beanFactory) {
        printBeanException("displayBeanFactoryGetBean", () -> beanFactory.getBean(User.class));
    }

    private static void printBeanException(String message, Runnable runnable) {
        System.err.println("========================================");
        System.err.println(message);
        try {
            runnable.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
