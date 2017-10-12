package io.ymq.thread.demo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述:创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
 * 线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
 *
 * @author yanpenglei
 * @create 2017-10-12 11:30
 **/
public class TestNewFixedThreadPool {

    public static void main(String[] args) {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {
                        String threadName = Thread.currentThread().getName();
                        System.out.println("执行：" + index + "，线程名称：" + threadName);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            });
        }

    }
}
