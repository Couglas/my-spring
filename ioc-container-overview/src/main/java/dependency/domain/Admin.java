package dependency.domain;

import dependency.annotation.AdminUser;

/**
 * 管理员
 *
 * @author couglas
 * @since 2024/5/12
 */
@AdminUser
public class Admin extends User {
    private String auth;

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "auth='" + auth + '\'' +
                "} " + super.toString();
    }
}
