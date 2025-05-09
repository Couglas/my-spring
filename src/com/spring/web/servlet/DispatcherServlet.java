package com.spring.web.servlet;

import com.spring.beans.BeanException;
import com.spring.web.context.WebApplicationContext;
import com.spring.web.context.support.AnnotationConfigWebApplicationContext;
import com.spring.web.method.HandlerMethod;
import com.spring.web.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 分发servlet
 *
 * @author zhenxingchen4
 * @since 2025/4/28
 */
public class DispatcherServlet extends HttpServlet {
    private static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    private WebApplicationContext parentApplicationContext;
    private WebApplicationContext webApplicationContext;
    private HandlerAdapter handlerAdapter;
    private HandlerMapping handlerMapping;
    private ViewResolver viewResolver;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        String contextConfigLocation = config.getInitParameter("contextConfigLocation");
        this.webApplicationContext = new AnnotationConfigWebApplicationContext(contextConfigLocation, this.parentApplicationContext);

        refresh();
    }

    private void refresh() {
        initHandlerMapping();
        initHandlerAdapter();
        initViewResolver();
    }

    private void initViewResolver() {
        try {
            this.viewResolver = (ViewResolver) this.webApplicationContext.getBean("viewResolver");
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
    }

    private void initHandlerAdapter() {
        try {
            this.handlerAdapter = (HandlerAdapter) this.webApplicationContext.getBean("handlerAdapter");
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
    }

    private void initHandlerMapping() {
        this.handlerMapping = new RequestMappingHandlerMapping(this.webApplicationContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        doDispatch(req, resp);
    }

    protected void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mav;
        HandlerMethod handler;

        try {
            handler = this.handlerMapping.getHandler(req);
            if (handler == null) {
                return;
            }

            mav = this.handlerAdapter.handle(req, resp, handler);

            render(req, resp, mav);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws Exception {
        if (mav == null) {
            resp.getWriter().flush();
            resp.getWriter().close();
            return;
        }
        String viewName = mav.getViewName();
        Map<String, Object> model = mav.getModel();
        View view = resolverViewName(viewName, model, req);
        view.render(model, req, resp);
    }

    private View resolverViewName(String viewName, Map<String, Object> model, HttpServletRequest req) throws Exception {
        View view = null;
        if (this.viewResolver != null) {
            view = viewResolver.resolveViewName(viewName);
        }

        return view;
    }

}
