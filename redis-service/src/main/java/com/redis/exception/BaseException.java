package com.redis.exception;


public class BaseException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = -2838067933461051117L;
    /**
     * 异常类型
     */
    private ExceptionType type;

    /**
     * 描述：构造函数
     * @param type 异常类型
     */
    public BaseException(ExceptionType type) {
        super();
        this.type = type;
    }

    /**
     * 描述：构造函数
     * @param type 异常类型
     * @param message 异常信息
     */
    public BaseException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    /**
     * 描述：构造函数
     * @param type 异常类型
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     * (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public BaseException(ExceptionType type, Throwable cause) {
        super(cause);
        this.type = type;
    }

    /**
     * 描述：构造函数
     * @param type 异常类型
     * @param message 异常信息
     * @param cause the cause (which is saved for later retrieval by the getCause() method).
     * (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public BaseException(ExceptionType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    /**
     * @return 异常类型
     */
    public ExceptionType getType() {
        return type;
    }

    /**
     * @param type 异常类型
     */
    protected void setType(ExceptionType type) {
        this.type = type;
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#toString()
     */
    @Override
    public String toString() {
        return getClass().getName() + ": {0x" + Integer.toHexString(type.getCode()) + "," + type.getDescribe() + "}" +
                (null==getMessage() ? "" : getMessage());
    }
}
