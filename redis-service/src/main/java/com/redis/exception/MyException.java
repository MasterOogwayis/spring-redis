package com.redis.exception;


public class MyException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1417600693122901059L;
	
	/**
	 * 
	 * @param type 异常类型
	 */
	public MyException(ExceptionType type) {
		super(type);
	}
	
	/**
	 * 
	 * @param type 异常类型
	 * @param message 异常信息
	 */
	public MyException(ExceptionType type, String message) {
		super(type, message);
	}
	
	/**
	 * 描述：
	 * @param type 异常类型
	 * @param cause the cause (which is saved for later retrieval by the getCause() method). 
	 * (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public MyException(ExceptionType type, Throwable cause) {
		super(type, cause);
	}
	
	/**
	 * 描述：
	 * @param type 异常类型
	 * @param message 异常信息
	 * @param cause the cause (which is saved for later retrieval by the getCause() method). 
	 * (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public MyException(ExceptionType type, String message, Throwable cause) {
		super(type, message, cause);
	}
	
	@Override
	public String getMessage(){
		return super.getMessage()==null?super.getType().getDescribe():super.getMessage();
	}
	
}
