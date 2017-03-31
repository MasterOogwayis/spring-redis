package com.redis.api.request;

import java.util.Date;

/**
 * @author ZhangShaowei on 2017/3/13 10:39
 */

public class ProductRequest extends BaseRequest{
    private static final long serialVersionUID = -2490547479420152759L;

    /**  */
    private String lineCode;

    /**  */
    private String productCode;

    /**  */
    private String productName;

    /**  */
    private Date operationStartDate;

    /**  */
    private Date operationEndDate;

    /**  */
    private Integer aheadDay;

    /**  */
    private String vehicleType;

    /**  */
    private String vehicleLevel;

    /**  */
    private Integer seats;

    /**  */
    private Integer price;

    /**  */
    private String description;

    /**  */
    private String remark;

    /**  */
    private String startTime;

    /**  */
    private String endTime;

    /**  */
    private Integer insurance;

    /**  */
    public String getLineCode() {
        return lineCode;
    }

    /**  */
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    /**  */
    public String getProductCode() {
        return productCode;
    }

    /**  */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**  */
    public String getProductName() {
        return productName;
    }

    /**  */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**  */
    public Date getOperationStartDate() {
        return operationStartDate;
    }

    /**  */
    public void setOperationStartDate(Date operationStartDate) {
        this.operationStartDate = operationStartDate;
    }

    /**  */
    public Date getOperationEndDate() {
        return operationEndDate;
    }

    /**  */
    public void setOperationEndDate(Date operationEndDate) {
        this.operationEndDate = operationEndDate;
    }

    /**  */
    public Integer getAheadDay() {
        return aheadDay;
    }

    /**  */
    public void setAheadDay(Integer aheadDay) {
        this.aheadDay = aheadDay;
    }

    /**  */
    public String getVehicleType() {
        return vehicleType;
    }

    /**  */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**  */
    public String getVehicleLevel() {
        return vehicleLevel;
    }

    /**  */
    public void setVehicleLevel(String vehicleLevel) {
        this.vehicleLevel = vehicleLevel;
    }

    /**  */
    public Integer getSeats() {
        return seats;
    }

    /**  */
    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    /**  */
    public Integer getPrice() {
        return price;
    }

    /**  */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**  */
    public String getDescription() {
        return description;
    }

    /**  */
    public void setDescription(String description) {
        this.description = description;
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
    public String getStartTime() {
        return startTime;
    }

    /**  */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**  */
    public String getEndTime() {
        return endTime;
    }

    /**  */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**  */
    public Integer getInsurance() {
        return insurance;
    }

    /**  */
    public void setInsurance(Integer insurance) {
        this.insurance = insurance;
    }
}
