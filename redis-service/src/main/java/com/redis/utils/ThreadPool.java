package com.redis.utils;

import com.redis.persistence.beans.BaseBean;
import com.redis.service.utils.Log4jUtils;
import org.apache.commons.logging.Log;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author ZhangShaowei on 2017/3/21 14:02 线程池 所有异步执行的线程请通过次工具类创建
 */

public final class ThreadPool {

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

    /**  */
    private static final int CORE_POOL_SIZE = 1;
    /**  */
    private static final int CORE_MUM_POOL_SIZE = 5;
    /**  */
    private static final long KEEP_ALIVE_TIME = 10L;
    /**  */
    private static final int QUEUE_SIZE = 30;


    static {
        pool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                CORE_MUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.MINUTES,
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
    public static void execute(final Consumer<BaseBean> consumer, final BaseBean obj) {
        pool.execute(() -> consumer.accept(obj));
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
