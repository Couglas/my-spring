package com.test.controller;

import com.spring.web.bind.annotation.RequestMapping;
import com.spring.web.bind.annotation.ResponseBody;
import com.spring.web.servlet.ModelAndView;
import com.test.SecondBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试类
 *
 * @author zhenxingchen4
 * @since 2025/4/28
 */
public class HelloWorldController {
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
}
