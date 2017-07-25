package com.xpm.web.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by xupingmao on 2017/7/25.
 */
public class MyStaticServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        res.addHeader("Content-Type", "text/plain");

        res.getWriter().write("PathInfo:" + pathInfo + "\n");

        while (req.getAttributeNames().hasMoreElements()) {
            String name = req.getAttributeNames().nextElement();
            String value = req.getAttribute(name).toString();
            res.getWriter().write(name + ":" + value + "\n");
        }

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
