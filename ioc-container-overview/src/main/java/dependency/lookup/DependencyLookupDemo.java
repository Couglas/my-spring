package dependency.lookup;

import dependency.domain.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 依赖查找demo
 *
 * @author couglas
 * @since 2024/5/12
 */
public class DependencyLookupDemo {
    // 1.定义xml文件
    // 2.启动应用上下文
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-lookup-context.xml");
        User user = (User) applicationContext.getBean("user");

        System.out.println(user);
    }
}
