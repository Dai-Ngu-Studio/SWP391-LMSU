package com.lmsu.bean.orderdata;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

public @Data
class OrderObj implements Serializable {
    private int id;
    private String memberID;
    private String memberName;
    private Date orderDate;
    private boolean lendMethod;
    private int activeStatus;

    public OrderObj() {
    }

    public OrderObj(int id, String memberID, String memberName, Date orderDate, boolean lendMethod, int activeStatus) {
        this.id = id;
        this.memberID = memberID;
        this.memberName = memberName;
        this.orderDate = orderDate;
        this.lendMethod = lendMethod;
        this.activeStatus = activeStatus;
    }
}
