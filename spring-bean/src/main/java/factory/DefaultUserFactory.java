package factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * UserFactory
 *
 * @author couglas
 * @since 2024/5/16
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

    @PostConstruct
    public void init() {
        System.out.println("postconstruct init ...");
    }

    @Override
    public void customerInit() {
        System.out.println("initMethod init...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet init...");
    }

    @PreDestroy
    public void preDestory() {
        System.out.println("preDestory ...");
    }

    @Override
    public void destroyFactory() {
        System.out.println("destoryFactory...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destory ...");
    }

    @Override
    public void finalize() throws Throwable {
        System.out.println("gc ....");
    }
}
