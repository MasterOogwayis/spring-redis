package com.redis.api.request;

import java.util.Date;

/**
 * @author ZhangShaowei on 2017/3/10 9:37
 */

public class SaleOrderIntercityRequest extends BaseRequest {

    private static final long serialVersionUID = -3705867474938381580L;

    /**
     * 采购订单号
     */
    private String purchaseNumber;

    /**
     * 产品信息
     */
    private ProductRequest product;

    /**
     * 车辆数
     */
    private Integer vehicleQuantity;

    /**
     * 乘客数
     */
    private Integer passengerQuantity;

    /**
     * 总金额
     */
    private Integer amount;

    /**
     * 用车日期
     */
    private Date useDate;

    /**
     * 班次
     */
    private String timeScope;

    /**
     * 备注
     */
    private String remark;

    /**  */
    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    /**  */
    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    /**  */
    public ProductRequest getProduct() {
        return product;
    }

    /**  */
    public void setProduct(ProductRequest product) {
        this.product = product;
    }

    /**  */
    public Integer getVehicleQuantity() {
        return vehicleQuantity;
    }

    /**  */
    public void setVehicleQuantity(Integer vehicleQuantity) {
        this.vehicleQuantity = vehicleQuantity;
    }

    /**  */
    public Integer getAmount() {
        return amount;
    }

    /**  */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**  */
    public Date getUseDate() {
        return useDate;
    }

    /**  */
    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    /**  */
    public String getTimeScope() {
        return timeScope;
    }

    /**  */
    public void setTimeScope(String timeScope) {
        this.timeScope = timeScope;
    }

    /**  */
    public String getRemark() {
        return remark;
    }

    /**  */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**  */
    public Integer getPassengerQuantity() {
        return passengerQuantity;
    }

    /**  */
    public void setPassengerQuantity(Integer passengerQuantity) {
        this.passengerQuantity = passengerQuantity;
    }
}
