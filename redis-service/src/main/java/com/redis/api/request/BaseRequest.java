package com.redis.api.request;

import java.io.Serializable;

/**
 * @author ZhangShaowei on 2017/3/9 14:41
 */

public class BaseRequest implements Serializable {

    private static final long serialVersionUID = -6195096505825187419L;

    /**
     * 操作员
     */
    private String opeartor;

    /**
     * 供应商编号
     */
    private String supplierNumber;

    /**  */
    public String getOpeartor() {
        return opeartor;
    }

    /**  */
    public void setOpeartor(String opeartor) {
        this.opeartor = opeartor;
    }

    /**  */
    public String getSupplierNumber() {
        return supplierNumber;
    }

    /**  */
    public void setSupplierNumber(String supplierNumber) {
        this.supplierNumber = supplierNumber;
    }
}
