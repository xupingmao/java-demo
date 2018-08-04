package com.xpm.hbase;

/**
 * Created by xupingmao on 2017/9/14.
 */
public class ProfileUtils {

    interface Procedure<R> {
        R execute() throws Exception;
    }

    public static <R> R timeit(String name, Procedure<R> procedure) throws Exception {
        long startTime = System.currentTimeMillis();
        R result = procedure.execute();
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("run [%s], time elapsed: %d ms", name, endTime-startTime));
        return result;
    }

}
