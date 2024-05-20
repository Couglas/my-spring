package dependency.lookup;

import domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Iterator;

/**
 * ObjectProvider示例
 *
 * @author couglas
 * @since 2024/5/18
 */
public class ObjectProviderDemo {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ObjectProviderDemo.class);
        applicationContext.refresh();

//        lookupByObjectProvider(applicationContext);
//        lookupIfAvaiable(applicationContext);
        lookupByStreamOps(applicationContext);

        applicationContext.close();
    }

    private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);
//        Iterator<String> iterator = beanProvider.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }

        beanProvider.stream().forEach(System.out::println);
    }

    private static void lookupIfAvaiable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> beanProvider = applicationContext.getBeanProvider(User.class);
        User user = beanProvider.getIfAvailable(User::createUser);
        System.out.println(user);
    }

    @Bean
    public String helloWorld() { // @bean没有指定名称，方法名就是bean名称
        return "hello world!";
    }

    @Bean
    public String message() {
        return "message";
    }

    private static void lookupByObjectProvider(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        ObjectProvider<String> beanProvider = annotationConfigApplicationContext.getBeanProvider(String.class);
        System.out.println(beanProvider.getObject());
        System.out.println(beanProvider);

    }

}
