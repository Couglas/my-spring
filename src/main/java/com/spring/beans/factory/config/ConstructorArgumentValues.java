package com.spring.beans.factory.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数类
 *
 * @author zhenxingchen4
 * @since 2025/4/24
 */
public class ConstructorArgumentValues {
    private final List<ConstructorArgumentValue> constructorArgumentValueList = new ArrayList<>();

    public void addArgumentValue(ConstructorArgumentValue constructorArgumentValue) {
        this.constructorArgumentValueList.add(constructorArgumentValue);
    }

    public ConstructorArgumentValue getIndexedArgument(int index) {
        return this.constructorArgumentValueList.get(index);
    }

    public int getArgumentCount() {
        return this.constructorArgumentValueList.size();
    }

    public boolean isEmpty() {
        return this.constructorArgumentValueList.isEmpty();
    }
}
