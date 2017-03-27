package com.redis.api.request;

/**
 * @author ZhangShaowei on 2017/3/13 17:10
 */

public class OrderRequest extends BaseRequest {

    private static final long serialVersionUID = -4764827401989726750L;

    /**
     *
     */
    private String orderNumber;

    /**
     *
     */
    private String remark;


    /**  */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**  */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**  */
    public String getRemark() {
        return remark;
    }

    /**  */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
