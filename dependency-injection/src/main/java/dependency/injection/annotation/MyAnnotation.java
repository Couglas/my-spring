package dependency.injection.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解
 *
 * @author couglas
 * @since 2024/5/25
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAnnotation {
}
