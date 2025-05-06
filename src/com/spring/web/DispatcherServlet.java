package com.spring.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 分发servlet
 *
 * @author zhenxingchen4
 * @since 2025/4/28
 */
public class DispatcherServlet extends HttpServlet {
    private static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
    private HandlerAdapter handlerAdapter;
    private HandlerMapping handlerMapping;
    private WebApplicationContext parentApplicationContext;
    private WebApplicationContext webApplicationContext;


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
    }

    private void initHandlerAdapter() {
        this.handlerAdapter = new RequestMappingHandlerAdapter(this.webApplicationContext);
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
        try {
            HandlerMethod handler = this.handlerMapping.getHandler(req);
            this.handlerAdapter.handle(req, resp, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
