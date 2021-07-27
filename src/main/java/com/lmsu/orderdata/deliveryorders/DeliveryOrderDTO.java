package com.lmsu.orderdata.deliveryorders;

import com.lmsu.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor

public @Data
class DeliveryOrderDTO implements Serializable {
    private int orderID;
    private String managerID;
    private UserDTO manager;
    private String deliverer;
    private Timestamp scheduledDeliveryTime;
    private String receiverName;
    private String phoneNumber;
    private String deliveryAddress1;
    private String deliveryAddress2;
    private String city;
    private String cityName;
    private String district;
    private String districtName;
    private String ward;
    private String wardName;
    private String trackingCode;
}
