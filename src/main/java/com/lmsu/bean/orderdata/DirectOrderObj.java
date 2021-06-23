package com.lmsu.bean.orderdata;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

public @Data
class DirectOrderObj implements Serializable {
    private int orderID;
    private String librarianID;
    private String librarianName;
    private Timestamp scheduledTime;

    public DirectOrderObj(){
    }

    public DirectOrderObj(int orderID, String librarianID, String librarianName, Timestamp scheduledTime) {
        this.orderID = orderID;
        this.librarianID = librarianID;
        this.librarianName = librarianName;
        this.scheduledTime = scheduledTime;
    }
}
