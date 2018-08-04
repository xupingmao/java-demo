package com.xpm.spring;

import org.junit.Assert;
import org.junit.Test;

/**
 * spring-retry测试
 * Created by xupingmao on 2017/12/21.
 */
public class RetryTest extends BaseSpringTest {

    @Test
    public void retry() {
        MySpringRetry bean = context.getBean(MySpringRetry.class);
        bean.call();
        Assert.assertTrue(bean.getCount() > 3);
    }

    @Test
    public void failAndRecover() {
        MySpringRetry bean = context.getBean(MySpringRetry.class);
        bean.failMethod();
        Assert.assertTrue(bean.isRecoverInvoked());
    }
}
