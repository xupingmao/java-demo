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

    @Retryable(maxAttempts = 5)
    public void call() {
        count++;
        if (count <= 3) {
            throw new RuntimeException("count <= 3");
        }
        System.out.println("Ok, finally done");
    }

    @Recover
    public void recover() {
        System.out.println("do nothing");
    }

    public int getCount() {
        return count;
    }
}
