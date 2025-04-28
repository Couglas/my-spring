package com.spring.context;

import java.util.EventListener;

/**
 * 应用监听器
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public class ApplicationListener implements EventListener {
    void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.toString());
    }
}
