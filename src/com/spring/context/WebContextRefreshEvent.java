package com.spring.context;

/**
 * web应用上下文刷新事件
 *
 * @author zhenxingchen4
 * @since 2025/4/30
 */
public class WebContextRefreshEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public WebContextRefreshEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return this.message;
    }
}
