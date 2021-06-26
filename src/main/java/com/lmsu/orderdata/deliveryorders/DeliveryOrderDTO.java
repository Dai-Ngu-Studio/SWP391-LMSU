package com.lmsu.orderdata.deliveryorders;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

public @Data
class DeliveryOrderDTO implements Serializable {
    private String orderID;
    private String managerID;
    private String deliverer;
    private Date scheduledDeliveryTime;
    private String phoneNumber;
    private String deliveryAddress1;
    private String deliveryAddress2;
    private String city;
    private String district;
    private String ward;
    private boolean isReturnOrder;

    public DeliveryOrderDTO() {
    }

    public DeliveryOrderDTO(String orderID, String managerID, String deliverer, Date scheduledDeliveryTime,
                            String phoneNumber, String deliveryAddress1, String deliveryAddress2, String city,
                            String district, String ward, boolean isReturnOrder) {
        this.orderID = orderID;
        this.managerID = managerID;
        this.deliverer = deliverer;
        this.scheduledDeliveryTime = scheduledDeliveryTime;
        this.phoneNumber = phoneNumber;
        this.deliveryAddress1 = deliveryAddress1;
        this.deliveryAddress2 = deliveryAddress2;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.isReturnOrder = isReturnOrder;
    }
}
