package test;

import com.redis.service.utils.Log4jUtils;
import org.apache.commons.logging.Log;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ZhangShaowei on 2017/3/28 17:44  spring 的线程池
 */
public class ThreadPool extends ThreadPoolTaskExecutor {

    /**  */
    private final Log log = Log4jUtils.getLog(this.getClass());

    /**
     * 核心线程池大小
     */
    private static final int CORE_POOL_SIZE = 1;
    /**
     * 最大线程池大小: CPU核心数：N
     * 计算密集型：N + 1
     * IO密集型：2N+1
     */
    private static final int CORE_MUM_POOL_SIZE = 5;
    /**
     * 线程池中超过corePoolSize数目的空闲线程最大存活时间 时间单位：秒s
     */
    private static final int KEEP_ALIVE_TIME = 60;
    /**
     * 阻塞任务队列大小
     */
    private static final int QUEUE_SIZE = 30;

    /**
     * 线程池初始化方法
     */
    @Override
    public void afterPropertiesSet() {
        //核心线程数
        super.setCorePoolSize(CORE_POOL_SIZE);
        //最大线程数
        super.setMaxPoolSize(CORE_MUM_POOL_SIZE);
        //队列最大长度
        super.setQueueCapacity(QUEUE_SIZE);
        //线程池维护线程所允许的空闲时间
        super.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        //线程池对拒绝任务(无线程可用)的处理策略
        super.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        super.afterPropertiesSet();
    }


}
