package io.ymq.thread.demo4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述:创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 *
 * @author yanpenglei
 * @create 2017-10-12 12:05
 **/
public class TestNewSingleThreadExecutor {

    public static void main(String[] args) {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {

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

