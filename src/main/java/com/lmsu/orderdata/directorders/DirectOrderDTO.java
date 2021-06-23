package com.lmsu.orderdata.directorders;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

public @Data
class DirectOrderDTO implements Serializable {
    private int orderID;
    private String librarianID;
    private Timestamp scheduledTime;

    public DirectOrderDTO() {
    }

    public DirectOrderDTO(int orderID, String librarianID, Timestamp scheduledTime) {
        this.orderID = orderID;
        this.librarianID = librarianID;
        this.scheduledTime = scheduledTime;
    }
}
