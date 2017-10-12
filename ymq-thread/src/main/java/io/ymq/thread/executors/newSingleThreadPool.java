package io.ymq.thread.executors;


import java.util.concurrent.*;

/**
 * 描述:创建线程数为1的线程池，由于使用了LinkedBlockingQueue所以maximumPoolSize 没用，corePoolSize为1表示线程数大小为1,满了就放入队列中，执行完了就从队列取一个。
 *
 * @author yanpenglei
 * @create 2017-10-12 14:47
 **/
public class newSingleThreadPool {

/*   public static ExecutorService newSingleThreadExecutor() {
        return new Executors.FinalizableDelegatedExecutorService
                (
                        new ThreadPoolExecutor(
                                1,
                                1,
                                0L,
                                TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>())
                );
    }

    public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                Executors.defaultThreadFactory(), defaultHandler);
    }*/
}
