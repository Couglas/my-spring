package com.spring.core.env;

/**
 * 环境接口
 *
 * @author zhenxingchen4
 * @since 2025/4/27
 */
public interface Environment extends PropertyResolver {
    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptProfiles(String... profiles);
}
