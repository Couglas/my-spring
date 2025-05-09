package com.spring.web.method.annotation;

import com.spring.http.converter.HttpMessageConverter;
import com.spring.web.bind.WebDataBinder;
import com.spring.web.bind.annotation.ResponseBody;
import com.spring.web.bind.support.WebBindingInitializer;
import com.spring.web.bind.support.WebDataBinderFactory;
import com.spring.web.method.HandlerMethod;
import com.spring.web.servlet.HandlerAdapter;
import com.spring.web.servlet.ModelAndView;

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
    private WebBindingInitializer webBindingInitializer;
    private HttpMessageConverter httpMessageConverter;

    public RequestMappingHandlerAdapter() {
    }

    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    public void setHttpMessageConverter(HttpMessageConverter httpMessageConverter) {
        this.httpMessageConverter = httpMessageConverter;
    }

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        return handleInternal(req, resp, (HandlerMethod) handler);
    }

    private ModelAndView handleInternal(HttpServletRequest req, HttpServletResponse resp, HandlerMethod handler) throws Exception {
        return invokeHandlerMethod(req, resp, handler);
    }

    protected ModelAndView invokeHandlerMethod(HttpServletRequest req, HttpServletResponse resp, HandlerMethod handlerMethod) throws Exception {
        ModelAndView mav = null;
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

        if (invocableMethod.isAnnotationPresent(ResponseBody.class)) {
            this.httpMessageConverter.write(result, resp);
        } else {
            if (result instanceof ModelAndView) {
                mav = (ModelAndView) result;
            } else if (result instanceof String) {
                mav = new ModelAndView();
                mav.setViewName((String) result);
            }
        }

        return mav;
    }


}
