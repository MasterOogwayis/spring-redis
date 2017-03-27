package com.redis.api.request;

import java.util.Date;

/**
 * @author ZhangShaowei on 2017/3/10 9:54
 */

public class VehicleTripRequest extends BaseRequest {

    /**
     * 班次
     */
    private String timeScope;

    /**
     * 预计用车时间起
     */
    private Date startTime;

    /**
     * 预计用车时间止
     */
    private Date endTime;



}
