package factory;

import domain.User;

/**
 * TODO
 *
 * @author couglas
 * @since 2024/5/16
 */
public interface UserFactory {
    default User createUser() {
        return User.createUser();
    }
}
