package com.xpm.spring;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

/**
 * Created by xupingmao on 2017/7/12.
 */
public class SpringContextLoaderListenerMain {

    public static void main(String[] args) {
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener();

        ServletContext context = new MyServletContext();
        ServletContextEvent servletContextEvent = new ServletContextEvent(context);
        // Java Servlet 会调用这个方法，异常会被抛出，如果需要拦截异常，继承它配置在web.xml中,或者使用装饰器即可
        contextLoaderListener.contextInitialized(servletContextEvent);
    }
}
