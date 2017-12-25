package com.xpm.jdk.test.concurrent;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join测试
 * Created by xupingmao on 2017/12/25.
 */
public class ForkJoinTest {

    public static class CountTask extends RecursiveTask<Long> {

        private long[] array;
        private int start;
        private int end;
        private final static int THRESHOLD = 10;

        /**
         *
         * @param array 数组
         * @param start 包含
         * @param end 不包含
         */
        public CountTask(long[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int size = end - start;
            if (size < THRESHOLD) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += i;
                }
                return sum;
            }

            int middle = (start + end) / 2;
            CountTask countTask1 = new CountTask(array, start, middle);
            CountTask countTask2 = new CountTask(array, middle, end);
            // 不要使用countTask1.fork(), countTask2.fork() 这样会浪费当前线程阻塞等待
            invokeAll(countTask1, countTask2);
            return countTask1.join() + countTask2.join();
        }
    }

    @Test
    public void runForkJoin() {
        int size = 1000;
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        Long sum = forkJoinPool.invoke(new CountTask(array, 0, size));
        System.out.println("sum=" + sum);
        Assert.assertEquals(Long.valueOf((size-1) * size / 2L), sum);
    }

}
