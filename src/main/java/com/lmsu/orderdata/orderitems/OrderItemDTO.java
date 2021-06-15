package com.lmsu.orderdata.orderitems;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

public @Data
class OrderItemDTO implements Serializable {
    private int id;
    private int orderID;
    private String memberID;
    private String bookID;
    private int lendStatus;
    private Date returnDeadline;
    private Date lendDate;
    private Date returnDate;

    public OrderItemDTO() {

    }

    public OrderItemDTO(int id, int orderID, String memberID, String bookID, int lendStatus, Date returnDeadline, Date lendDate, Date returnDate) {
        this.id = id;
        this.orderID = orderID;
        this.memberID = memberID;
        this.bookID = bookID;
        this.lendStatus = lendStatus;
        this.returnDeadline = returnDeadline;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
    }
}
