package com.lmsu.bean.orderdata;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

public @Data
class OrderItemObj implements Serializable {
    private int id;
    private int orderID;
    private String memberID;
    private String bookID;
    private String title;
    private int lendStatus;
    private Date returnDeadline;
    private Date lendDate;
    private Date returnDate;

    public OrderItemObj() {
    }

    public OrderItemObj(int id, int orderID, String memberID, String bookID, String title, int lendStatus,
                        Date returnDeadline, Date lendDate, Date returnDate) {
        this.id = id;
        this.orderID = orderID;
        this.memberID = memberID;
        this.bookID = bookID;
        this.title = title;
        this.lendStatus = lendStatus;
        this.returnDeadline = returnDeadline;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
    }
}
