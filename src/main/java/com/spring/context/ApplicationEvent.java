package com.spring.context;

import java.util.EventObject;

/**
 * 应用事件
 *
 * @author zhenxingchen4
 * @since 2025/4/23
 */
public class ApplicationEvent extends EventObject {


    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
