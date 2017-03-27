package com.redis.utils;

import com.redis.service.utils.Log4jUtils;
import org.apache.commons.logging.Log;

import java.io.Serializable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author ZhangShaowei on 2017/3/21 14:02 线程池 所有异步执行的线程请通过次工具类创建
 */

public final class ThreadPool {

    /**
     * Executors提供的线程池配置方案 ：
     *
     * 1、构造一个固定线程数目的线程池，配置的corePoolSize与maximumPoolSize大小相同，
     * 同时使用了一个无界LinkedBlockingQueue存放阻塞任务，因此多余的任务将存在再阻塞队列，
     * 不会由RejectedExecutionHandler处理
     *
     *  public static ExecutorService newFixedThreadPool(int nThreads) {
     *      return new ThreadPoolExecutor(
     *          nThreads, nThreads,
     *          0L, TimeUnit.MILLISECONDS,
     *          new LinkedBlockingQueue<Runnable>());
     *  }
     *
     *
     * 2、构造一个缓冲功能的线程池，配置corePoolSize=0，maximumPoolSize=Integer.MAX_VALUE，keepAliveTime=60s,
     * 以及一个无容量的阻塞队列 SynchronousQueue，因此任务提交之后，将会创建新的线程执行；线程空闲超过60s将会销毁
     *
     *  public static ExecutorService newCachedThreadPool() {
     *      return new ThreadPoolExecutor(
     *          0, Integer.MAX_VALUE,
     *          60L, TimeUnit.SECONDS,
     *          new SynchronousQueue<Runnable>());
     *  }
     *
     * 3、构造一个只支持一个线程的线程池，配置corePoolSize=maximumPoolSize=1，
     * 无界阻塞队列LinkedBlockingQueue；保证任务由一个线程串行执行
     *
     * public static ExecutorService newSingleThreadExecutor() {
     *  return new FinalizableDelegatedExecutorService(
     *      new ThreadPoolExecutor(
     *          1, 1,
     *          0L, TimeUnit.MILLISECONDS,
     *          new LinkedBlockingQueue<Runnable>()));
     *  }
     *
     *  4、构造有定时功能的线程池，配置corePoolSize，无界延迟阻塞队列DelayedWorkQueue；
     *  但是maximumPoolSize=Integer.MAX_VALUE，由于DelayedWorkQueue是无界队列，所以这个值是没有意义的
     *
     *  public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
     *      return new ScheduledThreadPoolExecutor(corePoolSize);
     *  }
     *
     *  public static ScheduledExecutorService newScheduledThreadPool(
     *      int corePoolSize, ThreadFactory threadFactory) {
     *      return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
     *  }
     *
     *  public ScheduledThreadPoolExecutor(int corePoolSize,
     *      ThreadFactory threadFactory) {
     *      super(corePoolSize, Integer.MAX_VALUE, 0, TimeUnit.NANOSECONDS,
     *      new DelayedWorkQueue(), threadFactory);
     *  }
     *
     */

    /**  */
    private ThreadPool() {
    }

    /**
     *
     */
    private static final Log log = Log4jUtils.getLog(ThreadPool.class);

    /**
     *
     */
    private static ThreadPoolExecutor pool = null;

    /**
     * 核心线程池大小
     */
    private static final int CORE_POOL_SIZE = 1;
    /**
     * 最大线程池大小: CPU核心数：N
     *      计算密集型：N + 1
     *      IO密集型：2N+1
     */
    private static final int CORE_MUM_POOL_SIZE = 5;
    /**
     * 线程池中超过corePoolSize数目的空闲线程最大存活时间 时间单位：TimeUnit
     */
    private static final long KEEP_ALIVE_TIME = 10L;
    /**
     * 阻塞任务队列大小
     */
    private static final int QUEUE_SIZE = 30;

    /**
     * 线程池初始化方法 ---- 阻塞的自定义线程池
     *
     * new ArrayBlockingQueue<Runnable>(30) 30容量的阻塞队列
     * new CustomThreadFactory()            定制的线程工厂
     * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时
     * 任务会交给RejectedExecutionHandler来处理
     *
     */
    static {
        pool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                CORE_MUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.MINUTES, //时间单位
                new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
                new CustomThreadFactory(),
                new CustomerRejectedExecutionHandler());
    }


    /**
     * 阻塞线程池 --- 异步执行的方法块 有参数 无返回值
     *
     * @param consumer FunctionalInterface
     * @param obj      param
     */
    public static <R extends Serializable> void execute(final Consumer<R> consumer, final R obj) {
        pool.execute(() -> consumer.accept((R) obj));
    }

    /**
     * 定制的线程工厂
     */
    private static class CustomThreadFactory implements ThreadFactory {
        /**
         * 线程安全的Integer,提供原子操作
         */
        private AtomicInteger count = new AtomicInteger(0);


        @Override
        public Thread newThread(final Runnable r) {
            Thread t = new Thread(r);
            String threadName = ThreadPool.class.getSimpleName() + count.addAndGet(1);
            log.info("new Thread:" + threadName);
            t.setName(threadName);
            return t;
        }
    }


    /**
     * 当提交任务数超过maxmumPoolSize+workQueue之和时,任务会交给RejectedExecutionHandler来处理
     * <p>
     * execute中提交到任务队列是用的offer方法，这个方法是非阻塞的，所以就会交给CustomRejectedExecutionHandler 来处理，
     * 所以对于大数据量的任务来说，这种线程池，如果不设置队列长度会OOM，设置队列长度，会有任务得不到处理，
     */
    private static class CustomerRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
            try {
                // 核心改造点，由blockingqueue的offer改成put阻塞方法
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                log.error(e);
            }
        }
    }


}
