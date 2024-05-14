package definition;

import domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * {@link org.springframework.beans.factory.config.BeanDefinition} BeanDefinition 示例
 *
 * @author couglas
 * @since 2024/5/14
 */
public class BeanDefinitionDemo {
    public static void main(String[] args) {
        // 通过BeanDefinitionBuilder构建bean
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        builder.addPropertyValue("id", 1)
                .addPropertyValue("name", "couglas");
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        System.out.println(beanDefinition.getBeanClass());

        // 通过AbstractBeanDefinition构建bean
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("id", 2)
                .add("name", "alex");
        genericBeanDefinition.setPropertyValues(mutablePropertyValues);
        System.out.println(genericBeanDefinition.getBeanClassName());
        System.out.println(genericBeanDefinition.getPropertyValues().get("id"));
        System.out.println(genericBeanDefinition.getPropertyValues().get("name"));
    }
}
