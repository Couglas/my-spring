package domain;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

/**
 * 用户类
 *
 * @author couglas
 * @since 2024/5/12
 */
public class User implements BeanNameAware {
    private Long id;
    private String name;
    private Week week;
    private Week[] weeks1;
    private List<Week> weeks2;
    private Resource configPath;
    private transient String beanName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Week[] getWeeks1() {
        return weeks1;
    }

    public void setWeeks1(Week[] weeks1) {
        this.weeks1 = weeks1;
    }

    public List<Week> getWeeks2() {
        return weeks2;
    }

    public void setWeeks2(List<Week> weeks2) {
        this.weeks2 = weeks2;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", week=" + week +
                ", weeks1=" + Arrays.toString(weeks1) +
                ", weeks2=" + weeks2 +
                ", configPath=" + configPath +
                '}';
    }

    public Resource getConfigPath() {
        return configPath;
    }

    public void setConfigPath(Resource configPath) {
        this.configPath = configPath;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public static User createUser() {
        User user = new User();
        user.setId(5L);
        user.setName("alia");
        return user;
    }

    // singleton和protyotype都会执行初始化回调
    @PostConstruct
    public void init() {
        System.out.println("[" + beanName + "] initing ...");
    }

    // 只有singleton会执行销毁回调
    @PreDestroy
    public void destory() {
        System.out.println("[" + beanName + "] destory ...");
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
