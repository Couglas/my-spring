package com.spring.context;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单应用事件发布器
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {
    private List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }
}
