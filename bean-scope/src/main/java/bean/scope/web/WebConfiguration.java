package bean.scope.web;

import domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * web配置类
 *
 * @author couglas
 * @since 2024/5/28
 */
@Configuration
@EnableWebMvc
public class WebConfiguration {

    @Bean
    @RequestScope
    public User user() {
        User user = new User();
        user.setId(8l);
        user.setName("xiaoming");
        return user;
    }
}
