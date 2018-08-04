package com.xpm.guava.event;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.MoreExecutors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;

/**
 * eventBus 事件驱动测试
 * eventBus提供同步和异步调用两种方式，支持并发
 * 对于每一个事件，eventBus会按照注册的顺序触发，遇到异常eventBus会catch住并打印出日志，所以用户最好自己处理异常
 * 如果一个事件没有对应的监听器，eventBus会创建一个DeadEvent把原事件包装一下发布出来
 * Created by xupingmao on 2017/7/10.
 */
public class EventBusTest {

    static EventBus eventBus = new EventBus();
    static EventBus asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(10));


    static void log(String fmt, Object...args) {
        fmt = "[" + Thread.currentThread().getName() + "]" + fmt + "\n";
        System.out.printf(fmt, args);
    }

    static class IntegerListener {
        @Subscribe
        @AllowConcurrentEvents
        public void handleEvent(Integer event) {
            System.out.println(Thread.currentThread().getName() + ":"
                    + this.getClass().getSimpleName() + " " + event.toString());
        }
    }

    static class FloatListener {
        @Subscribe
        public void handleEvent(Float event) {
            System.out.println(Thread.currentThread().getName() + ":"
                    + this.getClass().getSimpleName() + " " + event.toString());
        }
    }

    static class StringListener {
        @Subscribe
        @AllowConcurrentEvents
        public void handleEvent(String event) {
            log("%s %s",this.getClass().getSimpleName(), event);
        }
    }

    static class ObjectListener {
        @Subscribe
        public void handleEvent(Object event) {
            System.out.println(Thread.currentThread().getName() + ":"
                    + this.getClass().getSimpleName() + " " + event);
        }
    }

    static class MultiMethodListener {
        @Subscribe
        public void handleIntegerEvent(Integer event) {
            System.out.println(Thread.currentThread().getName() + ":"
                    + this.getClass().getSimpleName() + " Integer " + event);
        }

        @Subscribe
        public void handleStringEvent(String event) {
            System.out.println(Thread.currentThread().getName() + ":"
                    + this.getClass().getSimpleName() + " String " + event);
        }
    }

    static class ExceptionListener {
        @Subscribe
        public void handleException(Exception event) throws Exception {
            System.out.println(Thread.currentThread().getName() + ":"
                    + this.getClass().getSimpleName() + " " + event);
            throw new Exception(event);
        }
    }

    static void register(Object listener) {
        eventBus.register(listener);
        asyncEventBus.register(listener);
    }


    static {
        register(new IntegerListener());
        register(new FloatListener());
        register(new StringListener());
        register(new ObjectListener());
        register(new MultiMethodListener());
        register(new ExceptionListener());
    }

    // 所有的事件触发都是在同一个线程内
    @Test
    public void triggerIntegerEvent() {
        eventBus.post(10);
        /*
         IntegerListener 10
         MultiMethodListener Integer 10
         ObjectListener 10
         */
    }

    @Test
    public void triggerStringEvent() {
        eventBus.post("hello");
        /*
        StringListener hello
        MultiMethodListener String hello
        ObjectListener hello
        */
    }

    @Test
    public void triggerExceptionEvent() {
        eventBus.post(new Exception("test"));
        // 发生异常之后不会被阻断
        Assert.assertTrue(true);
    }

    @Test
    public void asyncEvent() {
        asyncEventBus.post("hello");
        log(" event posted");
    }
}
