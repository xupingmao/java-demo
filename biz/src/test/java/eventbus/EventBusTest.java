package eventbus;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by xupingmao on 2017/7/10.
 */
public class EventBusTest {

    static EventBus eventBus = new EventBus();

    static class IntegerListener {
        @Subscribe
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
        public void handleEvent(String event) {
            System.out.println(Thread.currentThread().getName() + ":"
                    + this.getClass().getSimpleName() + " " + event);
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


    static {
        eventBus.register(new IntegerListener());
        eventBus.register(new FloatListener());
        eventBus.register(new StringListener());
        eventBus.register(new ObjectListener());
        eventBus.register(new MultiMethodListener());
        eventBus.register(new ExceptionListener());
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
    }
}
