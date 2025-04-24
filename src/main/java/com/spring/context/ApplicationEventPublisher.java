package com.spring.context;

/**
 * 应用事件发布器
 *
 * @author zhenxingchen4
 * @since 2025/4/23
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
