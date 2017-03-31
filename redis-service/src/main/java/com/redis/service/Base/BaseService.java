package com.redis.service.Base;

import com.redis.service.utils.JacksonSerializer;
import com.redis.service.utils.Log4jUtils;
import org.apache.commons.logging.Log;

/**
 * @author ZhangShaowei on 2017/3/28 14:51
 */

public class BaseService extends JacksonSerializer {
    /**  */
    protected final Log log = Log4jUtils.getLog(this.getClass());

    /**
     * spring 配置的线程池
     */
//    @Resource(name = "threadPool")
//    private ThreadPoolTaskExecutor threadPool;
//
//    /**
//     * 异步执行
//     *
//     * @param consumer Consumer
//     * @param obj      R
//     */
//    protected <R extends Serializable> void execute(final Consumer<R> consumer, final R obj) {
//        this.threadPool.execute(() -> {
//            consumer.accept(obj);
//        });
//    }


}
