package com.spring.context;

/**
 * 上下文刷新事件
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public class ContextRefreshEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshEvent(Object source) {
        super(source);
    }

    @Override
    public String toString() {
        return this.message;
    }
}
