package com.redis.exception;

public interface ExceptionType {

	/**
	 * 描述：获取类型编码
	 * @return
	 * @author lurf
	 * @version 2.0.0 2012-11-1
	 * @since 2.0.0
	 */
	public int getCode();
	
	/**
	 * 描述：获取类型描述
	 * @return
	 * @author lurf
	 * @version 2.0.0 2012-11-1
	 * @since 2.0.0
	 */
	public String getDescribe();
}
