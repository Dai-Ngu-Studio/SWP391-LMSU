package com.lmsu.bean.orderdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor

public @Data
class DeliveryOrderObj implements Serializable {
    private int orderID;
    private String managerID;
    private String managerName;
    private String deliverer;
    private Timestamp scheduledDeliveryTime;
    private String receiverName;
    private String phoneNumber;
    private String deliveryAddress1;
    private String deliveryAddress2;
    private String city;
    private String district;
    private String ward;
    private String trackingCode;
}
