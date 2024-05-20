package dependency.lookup;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * bean初始化异常实例
 *
 * @author couglas
 * @since 2024/5/21
 */
public class BeanCreationExcetionDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(POJO.class);
        applicationContext.registerBeanDefinition("errorBean", builder.getBeanDefinition());
        applicationContext.refresh();
        applicationContext.close();
    }

    static class POJO implements InitializingBean {
        @PostConstruct
        public void init() throws Exception{
            throw new Exception("test Exception!");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            throw new Exception("my exception!");
        }
    }
}
