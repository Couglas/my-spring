package com.spring.web.method.annotation;

import com.spring.beans.BeanException;
import com.spring.web.bind.WebDataBinder;
import com.spring.web.bind.support.WebBindingInitializer;
import com.spring.web.bind.support.WebDataBinderFactory;
import com.spring.web.context.WebApplicationContext;
import com.spring.web.method.HandlerMethod;
import com.spring.web.servlet.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 请求映射处理适配器
 *
 * @author zhenxingchen4
 * @since 2025/5/6
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    private WebApplicationContext webApplicationContext;
    private WebBindingInitializer webBindingInitializer;

    public RequestMappingHandlerAdapter(WebApplicationContext webApplicationContext) {
        this.webApplicationContext = webApplicationContext;
        try {
            this.webBindingInitializer = (WebBindingInitializer) this.webApplicationContext.getBean("webBindingInitializer");
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        handleInternal(req, resp, (HandlerMethod) handler);
    }

    private void handleInternal(HttpServletRequest req, HttpServletResponse resp, HandlerMethod handler) throws Exception {
        if (handler == null) {
            return;
        }

        invokeHandlerMethod(req, resp, handler);
    }

    protected void invokeHandlerMethod(HttpServletRequest req, HttpServletResponse resp, HandlerMethod handlerMethod) throws Exception {
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];
        int i = 0;
        for (Parameter methodParameter : methodParameters) {
            Object methodParamObj = methodParameter.getType().newInstance();
            WebDataBinder wdb = binderFactory.createBinder(req, methodParamObj, methodParameter.getName());
            this.webBindingInitializer.initBinder(wdb);
            wdb.bind(req);
            methodParamObjs[i] = methodParamObj;
            i++;
        }


        Method invocableMethod = handlerMethod.getMethod();
        Object result = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
        resp.getWriter().append(result.toString());
    }


}
