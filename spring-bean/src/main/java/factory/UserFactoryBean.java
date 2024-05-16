package factory;

import domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * factoryBean实现
 *
 * @author couglas
 * @since 2024/5/16
 */
public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
