package com.lmsu.orderdata.orders;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

public @Data
class OrderDTO implements Serializable {
    private String id;
    private String memberID;
    private Date orderDate;
    private boolean lendMethod;

    public OrderDTO(String id, String memberID, Date orderDate, boolean lendMethod) {
        this.id = id;
        this.memberID = memberID;
        this.orderDate = orderDate;
        this.lendMethod = lendMethod;
    }
}
