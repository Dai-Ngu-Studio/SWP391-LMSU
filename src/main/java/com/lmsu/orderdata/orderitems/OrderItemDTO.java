package com.lmsu.orderdata.orderitems;

import com.lmsu.books.BookDTO;
import com.lmsu.orderdata.orders.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor

public @Data
class OrderItemDTO implements Serializable {
    private int id;
    private int orderID;
    private OrderDTO order;
    private int returnOrderID;
    private OrderDTO returnOrder;
    private String bookID;
    private BookDTO book;
    private int lendStatus;
    private Date returnDeadline;
    private Date lendDate;
    private Date returnDate;
    private BigDecimal penaltyAmount;
    private int penaltyStatus;
}
