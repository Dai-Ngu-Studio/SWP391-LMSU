package com.lmsu.bean.orderdata;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor

public @Data
class OrderItemObj implements Serializable {
    private int id;
    private int orderID;
    private int returnOrderID;
    private String memberID;
    private String memberName;
    private String bookID;
    private String title;
    private int lendStatus;
    private Date returnDeadline;
    private Date lendDate;
    private Date returnDate;
    private BigDecimal penaltyAmount;
    private int penaltyStatus;
}
