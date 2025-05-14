package com.test.controller;

import com.spring.beans.factory.annotation.Autowired;
import com.spring.web.bind.annotation.RequestMapping;
import com.spring.web.bind.annotation.ResponseBody;
import com.spring.web.servlet.ModelAndView;
import com.test.SecondBean;
import com.test.entity.User;
import com.test.service.Action;
import com.test.service.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试类
 *
 * @author zhenxingchen4
 * @since 2025/4/28
 */
public class HelloWorldController {
    @Autowired
    private UserService userService;
    @Autowired
    private Action action;
    @Autowired
    private Action actionService;

    @RequestMapping("/hello")
    public String doGet() {
        return "hello world！";
    }

    @RequestMapping("/param")
    public String paramTest(SecondBean secondBean) {
        return secondBean.getDesc();
    }

    @RequestMapping("/view")
    public ModelAndView viewTest(SecondBean secondBean) {
        Map<String, Object> model = new HashMap<>();
        model.put("desc", "test view");
        return new ModelAndView("test", model);
    }

    @RequestMapping("/body")
    @ResponseBody
    public SecondBean bodyTest(SecondBean secondBean) {
        secondBean.setDesc(secondBean.getDesc());
        secondBean.setMyDate(new Date());
        return secondBean;
    }

    @RequestMapping("/user")
    @ResponseBody
    public User userTest(User user) {
        return userService.getUser(user.getId());
    }

    @RequestMapping("/users")
    @ResponseBody
    public List<User> usersTest(User user) {
        return userService.getUserList(user.getId());
    }

    @RequestMapping("/user-info")
    @ResponseBody
    public User userInfoTest(User user) {
        return userService.getUserInfo(user.getId());
    }

    @RequestMapping("/aop")
    public void testAop() {
        action.doAction();
    }

    @RequestMapping("/aop1")
    public void testAop1() {
        actionService.doTest();
    }

}
