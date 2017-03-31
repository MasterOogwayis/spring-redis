package com.redis.exception;


public enum MyExceptionType implements ExceptionType {

    NO_DATA(-1,"未查询到您需要的数据"),
    PAID(0,"支付已成功")
    ;

    private int code;

    private String describe;

    private MyExceptionType(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescribe() {
        return describe;
    }
}
