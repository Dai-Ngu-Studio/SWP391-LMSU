package com.lmsu.orderdata.orderitems;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

public @Data
class OrderItemDTO implements Serializable {
    private String id;
    private String orderID;
    private String bookID;
    private boolean lendStatus;
    private Date returnDeadline;
    private Date lendDate;
    private Date returnDate;

    public OrderItemDTO(String id, String orderID, String bookID, boolean lendStatus, Date returnDeadline, Date lendDate, Date returnDate) {
        this.id = id;
        this.orderID = orderID;
        this.bookID = bookID;
        this.lendStatus = lendStatus;
        this.returnDeadline = returnDeadline;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
    }
}
