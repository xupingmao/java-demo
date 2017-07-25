package com.xpm.web.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by xupingmao on 2017/7/25.
 */
public class MyHelloServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        servletResponse.getOutputStream().write("hello".getBytes());
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
