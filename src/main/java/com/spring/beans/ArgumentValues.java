package com.spring.beans;

import java.util.*;

/**
 * 参数类
 *
 * @author zhenxingchen4
 * @since 2025/4/24
 */
public class ArgumentValues {
    private final List<ArgumentValue> argumentValueList = new ArrayList<>();

    public void addArgumentValue(ArgumentValue argumentValue) {
        this.argumentValueList.add(argumentValue);
    }

    public ArgumentValue getIndexedArgument(int index) {
        return this.argumentValueList.get(index);
    }

    public int getArgumentCount() {
        return this.argumentValueList.size();
    }

    public boolean isEmpty() {
        return this.argumentValueList.isEmpty();
    }
}
