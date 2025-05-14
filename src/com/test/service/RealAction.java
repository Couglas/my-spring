package com.test.service;

/**
 * 测试类
 *
 * @author zhenxingchen4
 * @since 2025/5/12
 */
public class RealAction implements Action {
    @Override
    public void doAction() {
        System.out.println("real action");
    }

    @Override
    public void doTest() {
        System.out.println("real action test");
    }
}
