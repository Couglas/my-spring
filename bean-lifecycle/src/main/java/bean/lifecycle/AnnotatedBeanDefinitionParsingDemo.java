package bean.lifecycle;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

/**
 * 注解 beandefinition解析示例
 *
 * @author couglas
 * @since 2024/6/1
 */
public class AnnotatedBeanDefinitionParsingDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        System.out.println(beanFactory.getBeanDefinitionCount());
        beanDefinitionReader.register(AnnotatedBeanDefinitionParsingDemo.class);
        System.out.println(beanFactory.getBeanDefinitionCount());

        // annotatedBeanDefinitionParsingDemo来自AnnotationBeanNameGenerator
        AnnotatedBeanDefinitionParsingDemo demo = beanFactory.getBean("annotatedBeanDefinitionParsingDemo", AnnotatedBeanDefinitionParsingDemo.class);
        System.out.println(demo);

    }

}
