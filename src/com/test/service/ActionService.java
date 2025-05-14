package com.test.service;

/**
 * 测试
 *
 * @author zhenxingchen4
 * @since 2025/5/14
 */
public class ActionService implements Action {

    @Override
    public void doTest() {
        System.out.println("action service do test");
    }

    @Override
    public void doAction() {
        System.out.println("action service do action");
    }
}
