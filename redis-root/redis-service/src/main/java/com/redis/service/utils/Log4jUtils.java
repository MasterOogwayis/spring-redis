package com.redis.service.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Log4jUtils {
	
	/**
	 * 描述：获取基本Log对象(log4j.xml里配置的root日志)
	 * @return 
	 * @author lurf
	 * @version 2.0.0
	 * @since 2.0.0
	 */
	public static Log getLog() {
		return LogFactory.getLog(Log4jUtils.class);
	}
	
	/**
	 * 描述：获取基本Log对象
	 * @param clazz
	 * @return
	 * @author lurf
	 * @version 2.0.0 2012-10-10
	 * @since 2.0.0
	 */
	public static Log getLog(Class<?> clazz) {
		return LogFactory.getLog(clazz);
	}
	
	/**
	 * 描述：根据日志名称获取Log对象
	 * @param loggerName 日志名称
	 * @return
	 * @author lurf
	 * @version 2.0.0 2012-10-19
	 * @since 2.0.0
	 */
	public static Log getLog(String loggerName) {
		return LogFactory.getLog(loggerName);
	}
	
}
