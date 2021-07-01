package com.lmsu.orderdata.orderitems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor

public @Data
class OrderItemDTO implements Serializable {
    private int id;
    private int orderID;
    private int returnOrderID;
    private String bookID;
    private int lendStatus;
    private Date returnDeadline;
    private Date lendDate;
    private Date returnDate;
}
