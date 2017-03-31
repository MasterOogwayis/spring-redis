package com.redis.api.request;

/**
 * @author ZhangShaowei on 2017/3/13 19:02
 */

public class PassengerRequest extends OrderRequest {
    private static final long serialVersionUID = 3735349454045352529L;

    private Integer passengers;

    /**  */
    public Integer getPassengers() {
        return passengers;
    }

    /**  */
    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }
}
