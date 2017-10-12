package io.ymq.thread.ThreadPoolExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @author yanpenglei
 * @create 2017-10-12 15:21
 **/
public class ThreadPoolExecutor {


/**
 * 构造函数参数

 1、corePoolSize 核心线程数大小，当线程数 < corePoolSize ，会创建线程执行 runnable

 2、maximumPoolSize 最大线程数， 当线程数 >= corePoolSize的时候，会把 runnable 放入 workQueue中

 3、keepAliveTime 保持存活时间，当线程数大于corePoolSize的空闲线程能保持的最大时间。

 4、unit 时间单位

 5、workQueue 保存任务的阻塞队列

 6、threadFactory 创建线程的工厂

 7、handler 拒绝策略

 任务执行顺序：

 1、当线程数小于 corePoolSize时，创建线程执行任务。

 2、当线程数大于等于 corePoolSize并且 workQueue 没有满时，放入workQueue中

 3、线程数大于等于 corePoolSize并且当 workQueue 满时，新任务新建线程运行，线程总数要小于 maximumPoolSize

 4、当线程总数等于 maximumPoolSize 并且 workQueue 满了的时候执行 handler 的 rejectedExecution。也就是拒绝策略。

 ThreadPoolExecutor默认有四个拒绝策略：

 1、ThreadPoolExecutor.AbortPolicy() 直接抛出异常RejectedExecutionException

 2、ThreadPoolExecutor.CallerRunsPolicy() 直接调用run方法并且阻塞执行

 3、ThreadPoolExecutor.DiscardPolicy() 直接丢弃后来的任务

 4、ThreadPoolExecutor.DiscardOldestPolicy() 丢弃在队列中队首的任务

 当然可以自己继承RejectedExecutionHandler来写拒绝策略.
 */

/*    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (workQueue == null || threadFactory == null || handler == null)
            throw new NullPointerException();
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.threadFactory = threadFactory;
        this.handler = handler;
    }*/

}
