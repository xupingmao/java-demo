package com.xpm.spring;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * Created by xupingmao on 2017/12/21.
 */
@Service
public class MySpringRetry {

    private int count = 0;
    private boolean recoverInvoked = false;

    @Retryable(maxAttempts = 5)
    public void call() {
        count++;
        if (count <= 3) {
            throw new RuntimeException("count <= 3");
        }
        System.out.println("Ok, finally done");
    }

    @Retryable(maxAttempts = 1)
    public void failMethod() {
        throw new RuntimeException("always fail");
    }

    @Recover
    public void recover(RuntimeException exception) {
        recoverInvoked = true;
        System.out.println("do recover");
    }

    public int getCount() {
        return count;
    }

    public boolean isRecoverInvoked() {
        return recoverInvoked;
    }
}
