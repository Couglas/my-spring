package dependency.injection;

import domain.User;

/**
 * UserHolder类
 *
 * @author couglas
 * @since 2024/5/21
 */
public class UserHolder {
    private User user;

    public UserHolder() {

    }

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                '}';
    }
}
